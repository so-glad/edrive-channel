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

    private String appKey = "600098230";

    private String appSecret = "559f25c2c1e7952cd37e69fb1eff0042";

    @Before
    public void prepare(){
        FileTemplate fileTemplate = new FileTemplate(appKey, appSecret);
        fileTemplate.setAccessToken("E59D69237620D8BC61EF69615E704B49");
        fileTemplate.setClient(new SimpleClientImpl());
        fileTemplate.setStreamSerializer(new JibxStreamSerializer());
        fileOperations = fileTemplate;
    }
    @Test
    public void testGetFileInfo(){
        FileInfo fileInfo = fileOperations.getFileInfo(813991314602825l, null, true, true);
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
