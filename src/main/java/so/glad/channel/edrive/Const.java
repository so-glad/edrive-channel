package so.glad.channel.edrive;

public class Const {

	public static String DEFAULT_ENCODING = "UTF-8";

	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String CHANNEL_ALIAS_EDRIVE = "edrive";

	public interface ConfigKey {
		String API_URL = "url.api";
		String UPLOAD_URL = "url.upload";
		String CLOUD_URL = "url.cloud";

		String ACT_USER_INFO = "act.user.info";
		String ACT_USER_PROFILE = "act.user.profile";
		String ACT_USER_UPLOAD  = "act.user.upload";
		String ACT_USER_AUTH = "act.user.auth";
		String ACT_USER_TOKEN = "act.user.token";

		String ACT_FILE_INFO = "act.file.info";
		String ACT_FILE_CREATE = "act.file.create";
		String ACT_FILE_STATUS = "act.file.status";
		String ACT_FILE_RENAME = "act.file.rename";
		String ACT_FILE_COPY = "act.file.copy";
		String ACT_FILE_MOVE = "act.file.move";
		String ACT_FILE_DELETE = "act.file.delete";
		String ACT_FOLDER_INFO = "act.file.folder.info";
		String ACT_FOLDER_CREATE = "act.file.folder.create";
		String ACT_FOLDER_RENAME = "act.file.folder.rename";
		String ACT_FOLDER_MOVE = "act.file.folder.move";
		String ACT_FOLDER_DELETE = "act.file.folder.delete";
		String ACT_LINK_CREATE = "act.file.link.create";
		String ACT_FILE_LIST = "act.file.list";
		String ACT_FILE_MEDIAS = "act.file.medias";
		String ACT_FILE_SEARCH = "act.file.search";
		String ACT_FILE_DIFF = "act.file.diff";
		String ACT_FILE_LINK_SINGLE = "act.file.link.single";
		String ACT_FILE_LINK_BATCH = "act.file.link.batch";

		String ACT_UPLOAD_SMALL = "act.upload.small";
		String ACT_UPLOAD_DATA = "act.upload.data";
		String ACT_UPLOAD_COMMIT = "act.upload.commit";
	}

	public interface GrantType {
		String AUTHORIZATION_CODE = "authorization_code";
		String REFRESH_TOKEN = "refresh_token";
		String PASSWORD = "password";
		String PASSPORT = "189_passport";
		String IMSI = "189_imsi";
		String E_ACCESS_TOKEN = "e189_accessToken";
	}
}
