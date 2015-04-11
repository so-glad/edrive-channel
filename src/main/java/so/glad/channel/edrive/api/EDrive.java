package so.glad.channel.edrive.api;

/**
 * Created by palmtale on 2015/3/26.
 */
public interface EDrive {

    UserOperations userOperations();

    FileOperations fileOperations();

    UploadOperations uploadOperations();
}
