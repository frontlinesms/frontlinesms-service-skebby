package com.skebby.gateways;

/**
 * 
 * @author Giancarlo Frison <giancarlo@gfrison.com>
 *
 */
public class SkebbyApiResponse {

	private GetCreditResponse get_credit;
	private SendSmsResponse send_sms_basic;
	private SendSmsResponse send_sms_classic;

	public GetCreditResponse getGet_credit() {
		return get_credit;
	}

	public void setGet_credit(GetCreditResponse get_credit) {
		this.get_credit = get_credit;
	}

	public SendSmsResponse getSend_sms_basic() {
		return send_sms_basic;
	}

	public void setSend_sms_basic(SendSmsResponse send_sms_basic) {
		this.send_sms_basic = send_sms_basic;
	}

	public SendSmsResponse getSend_sms_classic() {
		return send_sms_classic;
	}

	public void setSend_sms_classic(SendSmsResponse send_sms_classic) {
		this.send_sms_classic = send_sms_classic;
	}
	
}
