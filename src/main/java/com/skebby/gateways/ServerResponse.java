/**
 * 
 */
package com.skebby.gateways;

/**
 * @author Giancarlo Frison <giancarlo@gfrison.com>
 *
 */
public class ServerResponse {

	private String status;
	private SkebbyResult result = SkebbyResult.notReceived;
	protected Response response;

	public ServerResponse(String status2) {
		status=status2;
	}
	
	public ServerResponse() {
		
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SkebbyResult getResult() {
		if (status!=null) {
			result = SkebbyResult.valueOf(status);
		}
		return result;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	
	
}
