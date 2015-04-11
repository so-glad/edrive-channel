package so.glad.channel.edrive.api;

import so.glad.channel.edrive.entity.*;

/**
 * Created by palmtale on 2015/3/26.
 */
public interface FileOperations {

    FileInfo getFileInfo(Long fileId, String path, boolean mediaAttr, boolean iconOption);

    String getFileDownloadUrl(Long fileId, boolean isShort);

    File moveFile(Long fileId, String renamedFileName, Long destFolderId);

    /**
     *
     * @param folderId Folder's Id, when null, it is the default folder's id e.g.
     * @param fileType Set 0 or null when not limit, 1 for only files, and 2 for only folders
     * @param mediaType Set 0 or null when not limit, 1 for only picture, 2 for only music 3 for only video, 4 for only doc
     * @param mediaAttr Files' media attrSet 0 or null when not get, 1 for get
     * @param iconOption Files' icon Set 0 or null when not get, 1 for get
     * @param descending descending.
     * @param pageNum Page number
     * @param pageSize Page size
     * @param orderBy FileEntries' order style, "filename": Order by filename, "filesize": Order by file size, "createDate" "lastOptTime"
     * @return
     */
    FilesList listFiles(Long folderId, FileType fileType, MediaType mediaType, boolean mediaAttr,
                        IconOption iconOption, OrderBy orderBy, boolean descending, Integer pageNum, Integer pageSize
    );

    FolderInfo getFolderInfo(Long folderInd, String folderPath);
}
