package so.glad.channel.edrive;

import so.glad.channel.edrive.entity.ErrorMsg;

public class EdriveException extends Exception {
	public final static int INVALID_URL = -1;

	public final static int TRANSFER_ERROR = -2;

	public final static int REST_PARSE_ERROR = -3;
	
	public final static int REST_ERROR = -4;
	
	private int status;
	
    private ErrorMsg errorMsg;

    public EdriveException(int status) {
    	this.status = status;
    }
    
    public EdriveException(int status, Exception cause) {
    	super(cause);
    	this.status = status;
    }

    public EdriveException(int status, ErrorMsg errorMsg) {
    	this.status = status;
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return this.status;
    }
    
    public ErrorMsg getErrorMsg() {
        return this.errorMsg;
    }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("status=").append(status)
		  .append(", errorMsg=").append(errorMsg)
		  .append(", cause=").append(getCause());
		
		return sb.toString();
	}
}
