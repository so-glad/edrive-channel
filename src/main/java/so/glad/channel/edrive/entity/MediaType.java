package so.glad.channel.edrive.entity;

/**
 * Created by Cartoon on 2015/3/31.
 */
public enum MediaType {
    PICTURE(1), MUSIC(2), VIDEO(3), DOCUMENT(4);

    private MediaType(int code){
        this.code = code;
    }
    private int code;
    public int getCode(){
        return this.code;
    }
}
