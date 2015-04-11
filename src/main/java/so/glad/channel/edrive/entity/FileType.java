package so.glad.channel.edrive.entity;

/**
 * Created by Cartoon on 2015/3/31.
 */
public enum FileType {

    FILE(1), FOLDER(2);

    private FileType(int code){
        this.code = code;
    }

    private int code;

    public int getCode(){
        return code;
    }
}
