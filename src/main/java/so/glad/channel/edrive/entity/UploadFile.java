package so.glad.channel.edrive.entity;

public class UploadFile {
	private Long parentFolderId;
	
	private Long baseFileId;
	
	private String fileName;

	private String localPath;

	private Long UploadFileId;
	
	private Long UploadFileRangeStart;

	private Long UploadFileRangeEnd;
	
	private Long size;
	
	private String md5;
	
	private String lastWrite;
	
	public String getLastWrite() {
		return lastWrite;
	}

	public void setLastWrite(String lastWrite) {
		this.lastWrite = lastWrite;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public Long getBaseFileId() {
		return baseFileId;
	}

	public void setBaseFileId(Long baseFileId) {
		this.baseFileId = baseFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public Long getUploadFileId() {
		return UploadFileId;
	}

	public void setUploadFileId(Long uploadFileId) {
		UploadFileId = uploadFileId;
	}

	public Long getUploadFileRangeStart() {
		return UploadFileRangeStart;
	}

	public void setUploadFileRangeStart(Long uploadFileRangeStart) {
		UploadFileRangeStart = uploadFileRangeStart;
	}

	public Long getUploadFileRangeEnd() {
		return UploadFileRangeEnd;
	}

	public void setUploadFileRangeEnd(Long uploadFileRangeEnd) {
		UploadFileRangeEnd = uploadFileRangeEnd;
	}

	@Override
	public String toString() {
		return "UploadFile [parentFolderId=" + parentFolderId + ", baseFileId="
				+ baseFileId + ", fileName=" + fileName + ", localPath="
				+ localPath + ", UploadFileId=" + UploadFileId
				+ ", UploadFileRangeStart=" + UploadFileRangeStart
				+ ", UploadFileRangeEnd=" + UploadFileRangeEnd + "]";
	}
}
