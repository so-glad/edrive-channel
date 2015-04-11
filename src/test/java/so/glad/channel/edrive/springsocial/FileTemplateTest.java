package so.glad.channel.edrive.springsocial;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import so.glad.channel.edrive.api.FileOperations;
import so.glad.channel.edrive.entity.*;

import java.util.Date;

/**
 * @author palmtale
 * 2015/3/29.
 */
public class FileTemplateTest {

    private FileOperations fileOperations;
    @Before
    public void prepare(){
        fileOperations =  new EDriveServiceProvider("600098230", "559f25c2c1e7952cd37e69fb1eff0042")
                .getApi("E59D69237620D8BC61EF69615E704B49").fileOperations();
    }

    @Test
    public void testGetFileInfo(){
        Date date = new Date(1428481943l);
        Date date2 = new Date(1428482289l);
        FileInfo fileInfo = fileOperations.getFileInfo(null, "/我的文件/我的应用/Showoff/3.jpg", true, true);
        //0BB73CD2A8433BD84C413DF4D9B8387B
        //http://download.cloud.189.cn/v5/downloadFile.action?downloadRequest=1_E94D0B0537B03F52625C881DC11F3D2DA0F8BADE5036B681180D6092CB26086B97BE3B356847F16B384EF82AF8906AC2A49D4B77D4663F27824B4C32592BA37B62C902A54A664AFFC31103152FBB596C4A039ABF
        //http://download.cloud.189.cn/v5/downloadFile.action?downloadRequest=1_9E353FE0932751E7A35CC8664B3FDBAEC7CE6FF71BDC96CDB7BEFA69C0068229DEE77A60F37A9A84EE059C407A9ACF7EA70978D1BBDDECC60E2DDC69329B975C417291A1F270574610EDD04C3FD277C8EDF5F7CB
        Assert.assertNotNull(fileInfo);
    }

    @Test
    public void testGetFileDownloadUrl(){
        String link = fileOperations.getFileDownloadUrl(813991314602825l, true);
        Assert.assertNotNull(link);
    }

    @Test
    public void testMoveFile(){
        File file = fileOperations.moveFile(413841319418119l, null, 614991314921624l);
        Assert.assertNotNull(file);
    }

    @Test
    public void testListFiles(){
        FilesList fileList = fileOperations.listFiles(null, null, null, true, null, OrderBy.FILENAME, true, 1, 10);
        Assert.assertNotNull(fileList);
    }

    @Test
    public void testFolderInfo(){
        FolderInfo folderInfo = fileOperations.getFolderInfo(null, "/我的文件/我的应用/Showoff/Test");
        Assert.assertNotNull(folderInfo);
    }

    @Test
    public void test(){
        String filePaht = "";
    }
}
