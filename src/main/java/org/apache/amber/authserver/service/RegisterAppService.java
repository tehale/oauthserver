/**
 * 
 */
package org.apache.amber.authserver.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.apache.amber.authserver.Dao.RegisterAppDao;
import org.apache.amber.authserver.common.AESencrp;
import org.apache.amber.authserver.common.Common;
import org.apache.amber.authserver.common.OauthRegParam;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ankush
 * 
 */
@Service
public class RegisterAppService {

	private static final Logger logger = Logger
			.getLogger(RegisterAppService.class);
	private static final DecimalFormat timeFormat4 = new DecimalFormat(
			"0000;0000");
	
	public RegisterAppDao registerAppDao;

	public Object saveClientApp(OauthRegParam clientApp) {

		String clientId = generateClientID();
		String clientSecret;
		JSONObject resp = new JSONObject();
		Object resObj = null;
		try {
			clientSecret = generateClientSecret(clientApp.getName(), clientId,
					clientApp.getRedirectUrl());
			registerAppDao.saveClientApp(clientApp, clientId, clientSecret);

			resp.put("client_id", clientId);
			resp.put("client_secret", clientSecret);
			resp.put("redirect_uris", clientApp.getRedirectUrl());
			resp.put("auth_uri", Common.AUTH_URI);
			resp.put("token_uri", Common.TOKEN_URI);
			resObj=toObject(resp.toString());
		} catch (JSONException e) {

		} catch (Exception e) {
			// TODO create a response error json
			e.printStackTrace();
		}
		//Check for null conditions before sending
		return resObj;
		
		
	}

	private String generateClientID() {
		Calendar cal = Calendar.getInstance();
		String val = String.valueOf(cal.get(Calendar.YEAR));
		val += timeFormat4.format(cal.get(Calendar.DAY_OF_YEAR));
		val += UUID.randomUUID().toString().replaceAll("-", "");
		return val;
	}

	private String generateClientSecret(String appName, String clientId,
			String redirectUri) throws Exception {
		String value = appName +":"+ clientId +":"+ redirectUri+":";
		logger.debug("Value before encryption" + value);
		String clientSecret = AESencrp.encrypt(value);
		logger.debug("Client Secret " + clientSecret);
		return clientSecret;
	}

	/**
	 * Converts object containing response from back end(MCIM) to json string.
	 * 
	 * @param obj
	 *            Object to be converted to json string.
	 * @return Json string corresponding to object.
	 * @throws IOException
	 */
	private String toJson(Object obj) throws IOException {
		logger.debug("I/P parameters : obj = {} " + obj);
		ObjectMapper map = new ObjectMapper();
		logger.debug("Json string from json object : {}"
				+ map.writeValueAsString(obj).toString());
		return map.writeValueAsString(obj).toString();
	}

	/**
	 * Convert Json string into object which can be send to ui as data.
	 * 
	 * @param json
	 *            input json string.
	 * @return Mapping json string corresponds to input string.(By default
	 *         Jackson converts to Object of type Linked hashMap)
	 * @throws IOException
	 * 
	 * @throws ClassNotFoundException
	 */
	private Object toObject(String json) throws IOException,
			ClassNotFoundException {
		logger.debug("I/P parameters : json = {} " + json);
		ObjectMapper map = new ObjectMapper();
		Object data = (Object) map.readValue(json, Object.class);
		logger.debug("Json object corresponding to input strig  data={}" + data);
		return data;
	}

	/**
	 * @param registerAppDao the registerAppDao to set
	 */
	public void setRegisterAppDao(RegisterAppDao registerAppDao) {
		this.registerAppDao = registerAppDao;
	}

	/**
	 * @return the registerAppDao
	 */
	public RegisterAppDao getRegisterAppDao() {
		return registerAppDao;
	}
	public String getClientSecret(String clientId){
		 return registerAppDao.getClientSecret(clientId);
	}
	public String getRedirectUrl(String clientId){
		return registerAppDao.getRedirectUrl(clientId);
	}
}
