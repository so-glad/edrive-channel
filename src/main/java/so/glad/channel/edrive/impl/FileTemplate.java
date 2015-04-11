package so.glad.channel.edrive.impl;

import org.apache.commons.lang3.StringUtils;
import so.glad.channel.edrive.Const;
import so.glad.channel.edrive.EdriveConfig;
import so.glad.channel.edrive.api.FileOperations;
import so.glad.channel.edrive.entity.*;
import so.glad.channel.edrive.utils.CodecUtil;
import so.glad.client.http.HttpMethod;

import java.net.URLEncoder;

/**
 * Created by Cartoon on 2015/3/26.
 */
public class FileTemplate extends AbstractOperations implements FileOperations {

    public FileTemplate(String appKey, String appSecret) {
        super(appKey, appSecret);
    }

    @Override
    public FileInfo getFileInfo(Long fileId, String path, boolean mediaAttr, boolean iconOption) {
        if(this.getAuthorizeParameters() == null){
            throw new RuntimeException("Did not authorized");
        }
        if(fileId == null && StringUtils.isBlank(path)) {
            throw new RuntimeException("Never provided file identifier");
        }
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_INFO);
        StringBuilder dataBuilder = new StringBuilder();
        if(fileId != null) {
            dataBuilder.append("fileId=").append(fileId);
            dataBuilder.append(StringUtils.isBlank(path)? "" : "&filePath=" + path);
        } else {
            dataBuilder.append("filePath=").append(path);
        }
        if(mediaAttr) {
            dataBuilder.append("&mediaAttr=").append(1);
        }
        if(iconOption) {
            dataBuilder.append("&iconOption=").append(1);
        }
        return operate(command, dataBuilder.toString(), HttpMethod.GET, FileInfo.class);
    }

    @Override
    public String getFileDownloadUrl(Long fileId, boolean isShort) {
        if(this.getAuthorizeParameters() == null){
            throw new RuntimeException("Did not authorized");
        }
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_LINK_SINGLE);
        String data = "fileId=" + fileId + "&short=" + isShort;
        return operate(command, data, HttpMethod.GET, String.class);
    }

    @Override
    public File moveFile(Long fileId, String renamedFileName, Long destFolderId) {
        if(this.getAuthorizeParameters() == null){
            throw new RuntimeException("Did not authorized");
        }
        String command =  EdriveConfig.getProperty(Const.ConfigKey.ACT_FILE_COPY);
        String data = "fileId=" + fileId + "&destFileName=" + CodecUtil.encodeURL(renamedFileName) + "&destFolderId=" + destFolderId;
        return operate(command, data, HttpMethod.POST, File.class);
    }

    @Override
    public FilesList listFiles(Long folderId, FileType fileType, MediaType mediaType, boolean mediaAttr,
                               IconOption iconOption, OrderBy orderBy, boolean descending, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public FolderInfo getFolderInfo(Long folderInd, String folderPath) {
        return null;
    }
}
