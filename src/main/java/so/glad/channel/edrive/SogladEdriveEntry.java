package so.glad.channel.edrive;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import so.glad.channel.edrive.entity.*;
import so.glad.channel.edrive.utils.CodecUtil;
import so.glad.client.SimpleClient;
import so.glad.client.SimpleClientImpl;
import so.glad.client.http.HttpConnectionContext;

public class SogladEdriveEntry {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String endPoint = null;

	private String cloudPoint = null;

	private String transPoint = null;

	private AuthorizeParameters authorizeParameters;

	private SimpleClient client = new SimpleClientImpl();

	public SogladEdriveEntry() {
		this("");
	}

	public SogladEdriveEntry(String envParam){
		endPoint = EdriveConfig.getProperty(Const.ConfigKey.API_URL + envParam);
		cloudPoint = EdriveConfig.getProperty(Const.ConfigKey.CLOUD_URL + envParam);
		transPoint = EdriveConfig.getProperty(Const.ConfigKey.UPLOAD_URL + envParam);
	}

	/**
	 * Achieve authorize url
	 * @return authorize url
	 * @throws EdriveException
	 */
	public String getAuthorizeUrl(String callBackUrl, String responseType, String display, String state) throws EdriveException {
		String url = cloudPoint + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_AUTH);
		Long timestamp = new Date().getTime();

		String appKey =this.getAuthorizeParameters().getAppKey();
		String appSecret =this.getAuthorizeParameters().getAppSecret();

		String source = "appKey=" + appKey + "&timestamp=" + timestamp;

		logger.debug("getAccessTokenByCode() - source=" + source);
		String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
		if(responseType == null){
			responseType="code";
		}
		if(display == null) {
			display="default";
		}
		url = url + "?appKey=" + appKey + "&timestamp= " + timestamp + "&appSignature=" + dstAppSignature
				+ "&display=" + display + "&responseType=" + responseType + "&callbackUrl=" + callBackUrl;
		if(state!=null){
			url = url+"?state="+state;
		}
		logger.debug("getAccessTokenByCode() - url=" + url);
		return url;
	}

	/**
	 * Achieve User's base info
	 * @return User object carrying info deserialized from xml message
	 * @throws EdriveException
	 */
	public UserInfo getUserInfo() throws EdriveException {
		String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_INFO);
		String url = endPoint + command;
		logger.debug("getUserInfo() - url=" + url);
		if(this.getAuthorizeParameters() != null){
			String date = new Date().toString();
			String appSecret = this.getAuthorizeParameters().getAppSecret();
			String accessToken = this.getAuthorizeParameters().getAccessToken();
			String source = "AccessToken=" + accessToken + "&Operate=GET&RequestURI=" + command + "&Date="+ date;
			String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
			HttpConnectionContext httpConnectionContext = new HttpConnectionContext();
			httpConnectionContext.setHeaderProperties(new Properties());
			httpConnectionContext.getHeaderProperties().put("Date", date);
			httpConnectionContext.getHeaderProperties().put("AccessToken", accessToken);
			httpConnectionContext.getHeaderProperties().put("Signature", dstAppSignature);
			InputStream inputStream = client.getAsStream(url, httpConnectionContext);
			return (UserInfo)new JibxStreamSerializer().unmarshal(inputStream, Const.DEFAULT_ENCODING);
		}
		return null;
	}

	/**
	 * Achieve accessToken by account
	 * @return The access token;
	 * @throws EdriveException
	 */
	public String getAccessToken(String account) throws EdriveException {
		String url = endPoint + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_TOKEN);
		Long timestamp = new Date().getTime();
		String appKey = this.getAuthorizeParameters().getAppKey();
		String appSecret = this.getAuthorizeParameters().getAppSecret();
		
		String source = "appKey=" + appKey + "&timestamp=" + timestamp;
		String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
		url = url + "?appKey=" + appKey + "&timestamp=" + timestamp + "&appSignature=" + dstAppSignature + "&grantType=189_passport&account=" + account;
		logger.debug("getAccessTokenBy189passport() - url=" + url);
		return client.get(url);
	}

	/**
	 * Achieve accessToken by code
	 * @return access token
	 * @throws EdriveException
	 */
	public String getAccessTokenByCode(String code) throws EdriveException {
		String url = cloudPoint + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_TOKEN);
		Date date = new Date();
		Long timestamp = date.getTime();
		String appKey = this.getAuthorizeParameters().getAppKey();
		String appSecret = this.getAuthorizeParameters().getAppSecret();
		String source = "appKey="+appKey+"&timestamp="+timestamp;
		logger.debug("getAccessTokenByCode() - source=" + source);
		String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
		url = url + "?appKey=" + appKey + "&timestamp=" + timestamp + "&appSignature=" + dstAppSignature + "&grantType=authorization_code&code=" + code;
		logger.debug("getAccessTokenByCode() - url=" + url);
		HttpConnectionContext httpConnectionContext = new HttpConnectionContext();
		httpConnectionContext.setHeaderProperties(new Properties());
		httpConnectionContext.getHeaderProperties().put("Date", new Date().toString());
		httpConnectionContext.getHeaderProperties().put("Signature", dstAppSignature);
		return client.get(url, httpConnectionContext);
	}

	/**
	 * Achieve accessToken
	 * @return access token
	 * @throws EdriveException
	 */
	public String getAccessToken(String account, String password) throws EdriveException {
		String url = cloudPoint + EdriveConfig.getProperty(Const.ConfigKey.ACT_USER_TOKEN);
		Long timestamp = new Date().getTime();
		String appKey =this.getAuthorizeParameters().getAppKey();
		String appSecret =this.getAuthorizeParameters().getAppSecret();
		String source = "appKey=" + appKey + "&timestamp=" + timestamp;
		String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
		url = url + "?appKey=" + appKey + "&timestamp=" + timestamp + "&appSignature=" + dstAppSignature + "&grantType=password&loginName=" + account + "&password=" + password;
		logger.debug("getAccessTokenByPassword() - url=" + url);
		return client.get(url);
	}

	/**
	 * Achieve file info
	 * @param fileId
	 * @param filePath
	 * @param iconOption
	 * @param mediaAttr
	 * @return
	 * @throws EdriveException
	 * @throws UnsupportedEncodingException 
	 */
	public FileInfo getFileInfo(Long fileId, String filePath,Integer iconOption,Integer mediaAttr)
	throws EdriveException, UnsupportedEncodingException {
		StringBuffer url = new StringBuffer();
		url.append(endPoint).append(EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_INFO)).append("?");
		if(fileId != null){
			url.append("fileId=").append(fileId).append("&");
		}
		url.append("iconOption=").append(iconOption).append("&mediaAttr=").append(mediaAttr);

		if(filePath!=null){
			url.append("&filePath=").append(URLEncoder.encode(filePath, "UTF-8"));
		}
		
		logger.debug("listFiels() - url=" + url);
		if(this.getAuthorizeParameters()!=null){
			String responseContent = client.get(url.toString());
		}
		return null;
	}//&pageNum=1&pageSize=20
	
	/**
	 * Achieve folder info
	 * @param folderId
	 * @param folderPath
	 * @return
	 * @throws EdriveException
	 * @throws UnsupportedEncodingException 
	 */
	public FolderInfo getFolderInfo(Long folderId, String folderPath)
	throws EdriveException, UnsupportedEncodingException {
		StringBuffer url = new StringBuffer();
		url.append(endPoint).append(EdriveConfig.getProperty(Const.ConfigKey.ACT_FOLDER_INFO)).append("?");
		if(folderId != null){
			url.append("folderId=" + folderId).append("&");
		}
		if(folderPath!=null){
			url.append("folderPath=" + URLEncoder.encode(folderPath, "UTF-8"));
		}
		logger.debug("listFiels() - url=" + url);
		
		if(this.getAuthorizeParameters()!=null){
			//TODO
		}
		return null;
	}

	public Object getFileDownloadUrl(String file){
		String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_SEARCH);
		String url = endPoint + command  + "?folderId=614991314921624&fileName=" + file;
		logger.debug("getFileDownloadUrl() - url=" + url);
		if(this.getAuthorizeParameters() != null){
			String date = new Date().toString();
			String accessToken = this.getAuthorizeParameters().getAccessToken();
			String source = "AccessToken=" + accessToken + "&Operate=GET&RequestURI=" + command + "&Date=" + date;
			String appSecret = this.getAuthorizeParameters().getAppSecret();
			String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
			HttpConnectionContext httpConnectionContext = new HttpConnectionContext();
			httpConnectionContext.setHeaderProperties(new Properties());
			httpConnectionContext.getHeaderProperties().put("Date", date);
			httpConnectionContext.getHeaderProperties().put("AccessToken", accessToken);
			httpConnectionContext.getHeaderProperties().put("Signature", dstAppSignature);
			String responseContent = client.get(url, httpConnectionContext);
			System.out.print(responseContent);
			return null;
		}
		return null;
	}

	public Object uploadFile(Long fileId){
		String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_UPLOAD_SMALL);
		String url = EdriveConfig.getProperty(Const.ConfigKey.UPLOAD_URL)  + command;
		logger.debug("getFileDownloadUrl() - url=" + url);
		if(this.getAuthorizeParameters() != null){
			String date = new Date().toString();
			String accessToken = this.getAuthorizeParameters().getAccessToken();
			String source = "AccessToken=" + accessToken + "&Operate=PUT&RequestURI=" + command + "&Date=" + date;
			String appSecret = this.getAuthorizeParameters().getAppSecret();
			String dstAppSignature =  CodecUtil.hmacsha1(source, appSecret);
			HttpConnectionContext httpConnectionContext = new HttpConnectionContext();
			httpConnectionContext.setHeaderProperties(new Properties());
			httpConnectionContext.getHeaderProperties().put("Date", date);
			httpConnectionContext.getHeaderProperties().put("AccessToken", accessToken);
			httpConnectionContext.getHeaderProperties().put("Signature", dstAppSignature);
			httpConnectionContext.getHeaderProperties().put("Edrive-ParentFolderId", "513471128390002");
			httpConnectionContext.getHeaderProperties().put("Edrive-FileName", "Test.txt");
			String content = "Cartoon Daemon";
			httpConnectionContext.getHeaderProperties().put("Edrive-FileMD5", CodecUtil.md5Hash(content, Const.DEFAULT_ENCODING));
			httpConnectionContext.getHeaderProperties().put("Content-Length", Integer.toString(content.getBytes().length));
			String responseContent = client.post(url, content, httpConnectionContext);
			System.out.print(responseContent);
			return null;
		}
		return null;
	}

	public AuthorizeParameters getAuthorizeParameters() {
		return authorizeParameters;
	}

	public void setAuthorizeParameters(AuthorizeParameters authorizeParameters) {
		this.authorizeParameters = authorizeParameters;
	}

}
