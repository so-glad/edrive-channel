package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Cartoon
 * 2015/3/31.
 * <file>
 *     <id>1999</id>
 *     <name>test2.txt</name>
 *     <size>105</size>
 *     <md5>865670BE274F6C49B3E31A0C6728957A</md5>
 *     <createDate>2009-02-06 12:00:00</createDate>
 *     <rev>20110811120000</rev>
 * </file>
 */

@XmlRootElement
public class File extends FileEntry {

    private Long size;

    private String md5;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        if (!super.equals(o)) return false;
        File file = (File) o;
        return Objects.equal(size, file.size) &&
                Objects.equal(md5, file.md5);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), size, md5);
    }
}
