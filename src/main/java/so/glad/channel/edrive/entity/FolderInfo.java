package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FolderInfo {

	private Long id;

	private String path;

	private String name;
	
	private String createDate;

	private String lastOpTime;

	private Long rev;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getRev() {
		return rev;
	}

	public void setRev(Long rev) {
		this.rev = rev;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FolderInfo)) return false;
		FolderInfo that = (FolderInfo) o;
		return Objects.equal(id, that.id) &&
				Objects.equal(path, that.path) &&
				Objects.equal(name, that.name) &&
				Objects.equal(createDate, that.createDate) &&
				Objects.equal(lastOpTime, that.lastOpTime) &&
				Objects.equal(rev, that.rev);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, path, name, createDate, lastOpTime, rev);
	}
}
