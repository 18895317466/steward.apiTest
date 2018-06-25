package com.steward.commons;

/**
 * @author mastercheng
 *
 * @param <T>
 */
public class Result<T>{

	public static final int SUCCESS = 200 ;
	public static final int INVALID_PARAM = 400 ;
	public static final int SERVER_ERROR = 500 ;
	public static final int FAILURE = 600 ;

	public Result(){} ;

	public static <T> Result<T> createResult(){
		return new Result<T>() ;
	}

	public Result<T> success(String message){
		this.setCode(SUCCESS);
		this.setMessage(message);
		return this ;
	}

	public Result<T> failure(String message){
		this.setCode(FAILURE);
		this.setMessage(message);
		return this ;
	}

	public Result<T> invalidParams(String message){
		this.setCode(INVALID_PARAM);
		this.setMessage(message);
		return this ;
	}

	public Result<T> serverError(String message){
		this.setCode(SERVER_ERROR);
		this.setMessage(message);
		return this ;
	}

	public boolean isSuccess(){
		return SUCCESS == getCode() ;
	}

	private T content;
	private int code;
	private String message ;

	public T getContent() {
		return content;
	}

	public Result<T> setContent(T content) {
		this.content = content;
		return this ;
	}

	public int getCode() {
		return code;
	}

	public Result<T> setCode(int code) {
		this.code = code;
		return this ;
	}

	public String getMessage() {
		return message;
	}

	public Result<T> setMessage(String message) {
		this.message = message;
		return this ;
	}
}
