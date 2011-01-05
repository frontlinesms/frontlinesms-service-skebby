package com.skebby.gateways;

/**
 * 
 * @author Giancarlo Frison <giancarlo@gfrison.com>
 *
 */
public class GetCreditResponse extends ServerResponse{

	private double credit_left=0;
	private long classic_sms=0;
	private long basic_sms=0;
	
	public GetCreditResponse(String status, double creditLeft,
			long classicSms, long basicSms) {
		super(status);
		this.credit_left = creditLeft;
		this.classic_sms = classicSms;
		this.basic_sms = basicSms;
	}
	
	public GetCreditResponse() {
		
	}

	public double getCredit_left() {
		return credit_left;
	}

	public void setCredit_left(double credit_left) {
		this.credit_left = credit_left;
	}

	public long getClassic_sms() {
		return classic_sms;
	}

	public void setClassic_sms(long classic_sms) {
		this.classic_sms = classic_sms;
	}

	public long getBasic_sms() {
		return basic_sms;
	}

	public void setBasic_sms(long basic_sms) {
		this.basic_sms = basic_sms;
	}
	
	
	
}
