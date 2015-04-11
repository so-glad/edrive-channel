package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Cartoon on 2015/3/31.
 */
@XmlTransient
public abstract class FileEntry extends Root {

    private Long id;
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
        if (!(o instanceof FileEntry)) return false;
        FileEntry fileEntry = (FileEntry) o;
        return Objects.equal(id, fileEntry.id) &&
                Objects.equal(name, fileEntry.name) &&
                Objects.equal(createDate, fileEntry.createDate) &&
                Objects.equal(lastOpTime, fileEntry.lastOpTime) &&
                Objects.equal(rev, fileEntry.rev);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, createDate, lastOpTime, rev);
    }
}
