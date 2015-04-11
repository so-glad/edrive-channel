package so.glad.channel.edrive.entity;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Cartoon on 2015/3/31.
 */
@XmlRootElement(name = "listFiles")
public class FilesList extends Root {

    private String lastRev;

    private FolderBundle fileList;

    public String getLastRev() {
        return lastRev;
    }

    public void setLastRev(String lastRev) {
        this.lastRev = lastRev;
    }

    public FolderBundle getFileList() {
        return fileList;
    }

    public void setFileList(FolderBundle fileList) {
        this.fileList = fileList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilesList)) return false;
        FilesList filesList = (FilesList) o;
        return Objects.equal(lastRev, filesList.lastRev) &&
                Objects.equal(fileList, filesList.fileList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lastRev, fileList);
    }
}
