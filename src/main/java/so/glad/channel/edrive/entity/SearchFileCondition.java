package so.glad.channel.edrive.entity;

public class SearchFileCondition {

	private Long folderId;

	private Integer fileType;
	
	private Integer mediaType;
	
	private String fileName;
	
	private Integer iconOption=0;
	
	private Integer mediaAttr=0;
	
	private Integer pageNum;

	private Integer pageSize;
	
	private String orderBy;
	
	private boolean descending;
	
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isDescending() {
		return descending;
	}

	public void setDescending(boolean descending) {
		this.descending = descending;
	}

	public final Integer getPageNum() {
		return pageNum;
	}

	public final void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public final Integer getPageSize() {
		return pageSize;
	}

	public final void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getIconOption() {
		return iconOption;
	}

	public void setIconOption(Integer iconOption) {
		this.iconOption = iconOption;
	}
	
	public Integer getMediaAttr() {
		return mediaAttr;
	}

	public void setMediaAttr(Integer mediaAttr) {
		this.mediaAttr = mediaAttr;
	}

	@Override
	public String toString() {
		return "SearchFileCondition [descending=" + descending + ", fileName="
				+ fileName + ", fileType=" + fileType + ", folderId="
				+ folderId + ", iconOption=" + iconOption + ", mediaAttr="
				+ mediaAttr + ", mediaType=" + mediaType + ", orderBy="
				+ orderBy + ", pageNum=" + pageNum + ", pageSize=" + pageSize
				+ "]";
	}


}
