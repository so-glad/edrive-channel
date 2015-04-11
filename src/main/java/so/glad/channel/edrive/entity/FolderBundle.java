package so.glad.channel.edrive.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Cartoon on 2015/3/31.
 */
@XmlRootElement(name = "fileList")
public class FolderBundle {

    private Integer count;

    private List<File> fileList;

    private List<Folder> folderList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    @XmlElement(name = "file")
    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
    @XmlElement(name = "folder")
    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }
}
