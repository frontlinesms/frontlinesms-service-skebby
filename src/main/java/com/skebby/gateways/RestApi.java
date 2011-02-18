package com.skebby.gateways;

import static org.apache.commons.lang.StringUtils.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.h2.util.IOUtils;

import com.thoughtworks.xstream.XStream;

/**
 * Skebby client implementation http://www.skebby.com
 * http://www.skebby.it/s/pdf/gateway.pdf
 * 
 * @author Giancarlo Frison <giancarlo@gfrison.com> 
 *
 */
public class RestApi {

	private String username;
	private String password;
	private XStream xstream;

	private Logger log = Logger.getLogger("com.skebby.gateways");
	private Boolean ssl=false;


	public RestApi() {
		xstream = new XStream();
		xstream.alias("SkebbyApi_Public_Send_SmsEasy_Simple", SkebbyApiResponse.class);
	}
	
	public RestApi(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	private static String HOST="gateway.skebby.it";
	private static String URI="/api/send/smseasy/simple_rest.php";
	private static String SSL_HOST="secure.skebby.it";
	private static String SSL_URI="/gw/api/send/smseasy/simple_rest.php";

	/**
	 * retrieve the remaining credit and the number of sms user could send
	 * @return
	 * @throws IOException
	 */
	public GetCreditResponse credit() throws IOException {
		if(isEmpty(username) || isEmpty(password))
			throw new IllegalStateException("set username and password");
		HttpClient httpclient = client();
		log.info("credit request user:"+username);
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("method", "get_credit"));
		qparams.add(new BasicNameValuePair("username", username));
		qparams.add(new BasicNameValuePair("password", password));
		HttpGet get = new HttpGet(composeURI(qparams));
		log.debug(substringBeforeLast(get.getRequestLine().toString(),"&"));
		String r = convertStreamToString(httpclient.execute(get).getEntity().getContent());
		SkebbyApiResponse res = (SkebbyApiResponse) xstream.fromXML(r);
		return res.getGet_credit();
	}

	/**
	 * send sms via Skebby Api
	 * 
	 * @param basic        send basic (true) or classic (false) sms
	 * @param recipient    destination number (without + and 00 prefix)
	 * @param text         sms body
	 * @param sender       if classic, the sms will be sent by this sender
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public SendSmsResponse sendSms(boolean basic, String recipient, String text, String sender) throws ClientProtocolException, IOException {
		if(isEmpty(username) || isEmpty(password))
			throw new IllegalStateException("set username and password");
		HttpClient httpclient = client();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("method", "send_sms_"+((basic)?"basic":"classic")));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("recipient", removeStart(removeStart(recipient,"+"),"00")));
		params.add(new BasicNameValuePair("text", text));
		params.add(new BasicNameValuePair("charset", "UTF-8"));
		sender = removeStart(removeStart(sender,"+"),"00");
		if(isNumeric(sender)) 
			params.add(new BasicNameValuePair("sender_numeric", sender));
		else if(!isEmpty(sender))
			params.add(new BasicNameValuePair("sender_string", sender));
		HttpPost post = new HttpPost(composeURI(null));
		UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
		post.setEntity(ent);
		log.debug(post.getRequestLine());
		String apires = convertStreamToString(httpclient.execute(post).getEntity().getContent());
		log.debug("send sms response:"+apires);
		SkebbyApiResponse res = (SkebbyApiResponse) xstream.fromXML(apires);
		return basic? res.getSend_sms_basic():res.getSend_sms_classic();
	}

	protected HttpClient client() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
		HttpParams params = httpclient.getParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(params, "RestApi");
		return httpclient;
	}



	protected String convertStreamToString(InputStream is)	throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(is, baos);
		return baos.toString();	
	}

	public void setUseSSL(Boolean propertyValue) {
		ssl = propertyValue;
	}
	protected URI composeURI(List<NameValuePair> qparams) {
		URI uri = null;
		try {
			uri = URIUtils.createURI(ssl?"https":"http", ssl?SSL_HOST:HOST, -1, ssl?SSL_URI:URI, 
			    qparams!=null?URLEncodedUtils.format(qparams, "UTF-8"):null, null);
		} catch (URISyntaxException e) {
			log.error(e.getMessage(),e);
			return null;
		}
		return uri;
	}


}
