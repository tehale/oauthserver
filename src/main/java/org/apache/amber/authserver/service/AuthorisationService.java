/**
 * 
 */
package org.apache.amber.authserver.service;

import org.apache.amber.authserver.common.User;
import org.springframework.stereotype.Service;

/**
 * @author ankush
 *
 */

public class AuthorisationService {

	/*protected AuthorizationRequest extractAuthorizationRequest(HttpServletRequest request) {
	    String responseType = request.getParameter("response_type");
	    String clientId = request.getParameter("client_id");
	    String redirectUri = request.getParameter("redirect_uri");

	    List<String> requestedScopes = null;
	    if (StringUtils.isNotBlank(request.getParameter("scope"))) {
	      requestedScopes = Arrays.asList(request.getParameter("scope").split(","));
	    }

	    String state = request.getParameter("state");
	    String authState = getAuthStateValue();

	    return new AuthorizationRequest(responseType, clientId, redirectUri, requestedScopes, state, authState);
	  }
*/

	public boolean authoriseUser(User user){
		//Authorise user against database
		return true;
	}
}
