package so.glad.channel.edrive.entity;

/**
 * Created by Cartoon on 2015/3/31.
 */
public enum OrderBy {
    FILENAME("filename"), FILESIZE("filesize"), CREATEDATE("createDate"), lastOpTime("lastOpTime");

    private OrderBy(String code){
        this.code = code;
    }
    private String code;
    public String getCode(){
        return this.code;
    }
}
