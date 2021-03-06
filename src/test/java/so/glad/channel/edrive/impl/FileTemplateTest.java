package so.glad.channel.edrive.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import so.glad.channel.edrive.api.FileOperations;
import so.glad.channel.edrive.entity.File;
import so.glad.channel.edrive.entity.FileInfo;
import so.glad.channel.edrive.entity.JibxStreamSerializer;
import so.glad.client.SimpleClientImpl;

/**
 * @author Cartoon
 * on 2015/3/27.
 */
public class FileTemplateTest {
    private FileOperations fileOperations;

    private String appKey = "600080118";//"600098230";

    private String appSecret = "21184e4f8575ec23b909fde70f6f9ce0";//"559f25c2c1e7952cd37e69fb1eff0042";

    @Before
    public void prepare(){
        FileTemplate fileTemplate = new FileTemplate(appKey, appSecret);
        fileTemplate.setAccessToken("0BB73CD2A8433BD84C413DF4D9B8387B"/*"E59D69237620D8BC61EF69615E704B49"*/);
        fileTemplate.setClient(new SimpleClientImpl());
        fileTemplate.setStreamSerializer(new JibxStreamSerializer());
        fileOperations = fileTemplate;
    }

    @Test
    public void testGetFileInfo(){
        FileInfo fileInfo = fileOperations.getFileInfo(null, "/我的文件/我的应用/AMOC/csgo/maps/ze_ATIX_panic_b3t_p.bsp", true, true);
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
}
