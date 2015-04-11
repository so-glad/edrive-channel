package so.glad.channel.edrive.springsocial;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;
import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.EdriveConfig;
import so.glad.channel.edrive.api.FileOperations;
import so.glad.channel.edrive.entity.*;
import so.glad.channel.edrive.utils.CodecUtil;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author palmtale
 * 2015/3/26.
 */
public class FileTemplate extends AbstractOperations implements FileOperations {

    public FileTemplate(RestTemplate restTemplate, boolean isAuthorized) {
        super(isAuthorized);
        super.setRestTemplate(restTemplate);
    }

    @Override
    public FileInfo getFileInfo(Long fileId, String path, boolean mediaAttr, boolean iconOption) {
        requireAuthorization();
        if(fileId == null && StringUtils.isBlank(path)) {
            throw new RuntimeException("Never provided file identifier");
        }
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_INFO);
        StringBuilder dataBuilder = new StringBuilder();
        if(fileId != null) {
            dataBuilder.append("fileId=").append(fileId);
            dataBuilder.append(StringUtils.isBlank(path)? "" : "&filePath=" + CodecUtil.encodeURL(path));
        } else {
            dataBuilder.append("filePath=").append(CodecUtil.encodeURL(path));
        }
        dataBuilder.append(mediaAttr ? "&mediaAttr=1" : "");
        dataBuilder.append(iconOption ? "&iconOption=1" : "");
        String url = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command + "?" + dataBuilder.toString();
        getLogger().debug(command + " - url=" + url);
        return getRestTemplate().getForObject(URI.create(url), FileInfo.class);
    }

    @Override
    public String getFileDownloadUrl(Long fileId, boolean isShort) {
        requireAuthorization();
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_LINK_SINGLE);
        String url = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command + "?fileId=" + fileId + "&short=" + isShort;
        getLogger().debug(command + " - url=" + url);
        return getRestTemplate().getForObject(URI.create(url), String.class);
    }

    @Override
    public File moveFile(Long fileId, String renamedFileName, Long destFolderId) {
        requireAuthorization();
        String command = EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_MOVE);
        String url  = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command;
        getLogger().debug(command + " - url=" + url);
        Map<String, Object> requestParams = Maps.newHashMap();
        requestParams.put("fileId", fileId);
        requestParams.put("destFileName", renamedFileName);
        requestParams.put("destFolderId", destFolderId);
        return getRestTemplate().postForObject(url, null, File.class, requestParams);
    }

    @Override
    public FilesList listFiles(Long folderId, FileType fileType, MediaType mediaType, boolean mediaAttr,
                               IconOption iconOption, OrderBy orderBy, boolean descending, Integer pageNum, Integer pageSize) {
        requireAuthorization();
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_LIST);
        String url = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command +  "?" + concatListFileParams(folderId, fileType, mediaType, mediaAttr,
                iconOption, orderBy, descending, pageNum, pageSize);
        getLogger().debug(command + " - url=" + url);
        return getRestTemplate().getForObject(URI.create(url), FilesList.class);
    }

    @Override
    public FolderInfo getFolderInfo(Long folderId, String folderPath) {
        requireAuthorization();
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FOLDER_INFO);
        String url = EdriveConfig.getProperty(Const.ConfigKey.API_URL) + command;
        if (folderId != null) {
            url += ("?folderId=" + folderId);
            url += (folderPath == null ? "" : "&folderPath=" + CodecUtil.encodeURL(folderPath));
        } else if(folderPath != null) {
            url += ("?folderPath=" + CodecUtil.encodeURL(folderPath));
        }
        getLogger().debug(command + " - url=" + url);
        return getRestTemplate().getForObject(URI.create(url), FolderInfo.class);
    }

    private String concatListFileParams(Long folderId, FileType fileType, MediaType mediaType, boolean mediaAttr, IconOption iconOption,
                                        OrderBy orderBy, boolean descending, Integer pageNum, Integer pageSize){
        List<String> params = Lists.newArrayList();
        if(folderId != null) {
            params.add("folderId=" + folderId);
        }
        if(fileType != null) {
            params.add("fileType=" + fileType.getCode());
        }
        if(mediaType != null) {
            params.add("mediaType=" + mediaType.getCode());
        }
        params.add("mediaAttr=" + (mediaAttr ? "1" : "0"));
        if(iconOption != null) {
            params.add("iconOption=" + iconOption.getCode());
        }
        if(orderBy != null) {
            params.add("orderBy=" + orderBy.getCode());
        }
        params.add("descending=" + descending );
        if(pageNum != null) {
            params.add("pageNum=" + pageNum);
        }
        if(orderBy != null) {
            params.add("pageSize=" + pageSize);
        }
        return StringUtils.join(params, "&");
    }
}
