/**
 * 
 */
package com.skebby.gateways;

/**
 * @author Giancarlo Frison <giancarlo@gfrison.com>
 *
 */
public class SendSmsResponse extends ServerResponse {

	private long remaining_sms;
	public long getRemaining_sms() {
		return remaining_sms;
	}

	public void setRemaining_sms(long remaining_sms) {
		this.remaining_sms = remaining_sms;
	}

	
}
