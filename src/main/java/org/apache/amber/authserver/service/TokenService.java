/**
 * 
 */
package org.apache.amber.authserver.service;

import java.util.List;

import org.apache.amber.authserver.Dao.RegisterAppDao;

/**
 * Validating token request and Storing the generated tokens.
 * 
 * @author ankush
 * 
 */
public class TokenService {

	public RegisterAppDao registerAppDao;

	public boolean validateClientId(String clientId) {
	List<String> clientIds=registerAppDao.getAllClientIds();
	return clientIds.contains(clientId);
	}

	/*public String getClientId(String authCode) {

	}
*/
	public boolean verifyClientSecret(String clientId, String clientSecret) {
		if (clientId != null && clientSecret != null) {
			return (clientSecret.equals(registerAppDao
					.getClientSecret(clientId)) ? true : false);
		} else {
			return false;
		}
	}

	public boolean verifyRedirectUri(String clientId, String redirectUri) {

		String registeredRedirectUri = registerAppDao.getRedirectUrl(clientId);
		if (registeredRedirectUri.equals(redirectUri)) {
			return true;
		} else {
			return false;
		}
		
	}
}
