package com.skebby.gateways;

/**
 * 
 * @author Giancarlo Frison <giancarlo@gfrison.com>
 *
 */
public class Response {

	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
