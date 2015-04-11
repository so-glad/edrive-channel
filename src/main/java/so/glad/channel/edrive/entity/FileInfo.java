package so.glad.channel.edrive.entity;

import java.util.List;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fileInfo")
public class FileInfo extends Root {

	private Long id;

	private Long parentFolderId;	//

	private String path;	//

	private String name;	

	private Long size;

	private String md5;

	private String createDate;	

	private String lastOpTime;

	private Integer mediaType;

	private List<MediaAttr> mediaAttrs;

	private Icon icon;

	private Long rev;

	private String fileDownloadUrl;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FileInfo)) return false;
		FileInfo fileInfo = (FileInfo) o;
		return Objects.equal(id, fileInfo.id) &&
				Objects.equal(parentFolderId, fileInfo.parentFolderId) &&
				Objects.equal(path, fileInfo.path) &&
				Objects.equal(name, fileInfo.name) &&
				Objects.equal(size, fileInfo.size) &&
				Objects.equal(md5, fileInfo.md5) &&
				Objects.equal(createDate, fileInfo.createDate) &&
				Objects.equal(lastOpTime, fileInfo.lastOpTime) &&
				Objects.equal(mediaType, fileInfo.mediaType) &&
				Objects.equal(mediaAttrs, fileInfo.mediaAttrs) &&
				Objects.equal(icon, fileInfo.icon) &&
				Objects.equal(rev, fileInfo.rev) &&
				Objects.equal(fileDownloadUrl, fileInfo.fileDownloadUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, parentFolderId, path, name, size, md5, createDate, lastOpTime, mediaType, mediaAttrs, icon, rev, fileDownloadUrl);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLastOpTime() {
		return lastOpTime;
	}

	public void setLastOpTime(String lastOpTime) {
		this.lastOpTime = lastOpTime;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public List<MediaAttr> getMediaAttrs() {
		return mediaAttrs;
	}

	public void setMediaAttrs(List<MediaAttr> mediaAttrs) {
		this.mediaAttrs = mediaAttrs;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Long getRev() {
		return rev;
	}

	public void setRev(Long rev) {
		this.rev = rev;
	}

	public String getFileDownloadUrl() {
		return fileDownloadUrl;
	}

	public void setFileDownloadUrl(String fileDownloadUrl) {
		this.fileDownloadUrl = fileDownloadUrl;
	}
}
