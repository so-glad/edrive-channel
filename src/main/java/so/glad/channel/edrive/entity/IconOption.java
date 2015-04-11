package so.glad.channel.edrive.entity;

/**
 * Created by Cartoon on 2015/3/31.
 */
public enum IconOption {
    SMALL(1), MIDDLE(2), LARGE(4), THUMBNAIL(8);
    private IconOption(int code){
        this.code = code;
    }

    private int code;

    public int getCode(){
        return code;
    }
}
