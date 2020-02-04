package com.dawnwing.framework.core;

/**
 * @description: <业务错误封装类>
 * @author: w.xL
 * @date: 2018-3-1
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 6682471604042248647L;

	private Integer errorCode;
	
	public ServiceException(){
        super();
    }
	
	public ServiceException(String message){
        super(message);
    }
	
	public ServiceException(String message, Integer errorCode){
		super(message);
		setErrorCode(errorCode);
	}
	
	public ServiceException(String message, Throwable throwable){
        super(message, throwable);
    }
	
	public ServiceException(String message, Integer errorCode, Throwable throwable){
        super(message, throwable);
        setErrorCode(errorCode);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
