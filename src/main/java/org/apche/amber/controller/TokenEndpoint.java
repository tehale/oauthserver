package org.apche.amber.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.amber.authserver.service.TokenService;
import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuer;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.OAuthTokenRequest;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.error.OAuthError.TokenResponse;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 *
 *
 */
@RequestMapping("/token")
public class TokenEndpoint {

	TokenService tokenService;

	@RequestMapping(method = RequestMethod.GET, consumes = "application/x-www-form-urlencoded", produces = "application/json")
	public Response authorize(@Context HttpServletRequest request)
			throws OAuthSystemException {

		OAuthTokenRequest oauthRequest = null;

		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try {
			oauthRequest = new OAuthTokenRequest(request);
			String clientId;
			// verify client id
			// verify client secret,auth code and its expirty,redirect url.
			boolean isClientIdValid = tokenService
					.validateClientId(oauthRequest
							.getParam(OAuth.OAUTH_CLIENT_ID));

			// check if clientid is valid
			if (!isClientIdValid) {
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						.setErrorDescription("client_id not found")
						.buildJSONMessage();
				return Response.status(response.getResponseStatus())
						.entity(response.getBody()).build();
			} else {
				clientId = oauthRequest.getParam(OAuth.OAUTH_CLIENT_ID);
			}
			//Check if client Secret is invalid
			boolean isClientSecretValid=tokenService.verifyClientSecret(clientId, oauthRequest.getParam(OAuth.OAUTH_CLIENT_SECRET));
			if(!isClientSecretValid){
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						.setErrorDescription("client_secret is incorrect ")
						.buildJSONMessage();
				return Response.status(response.getResponseStatus())
						.entity(response.getBody()).build();

			}
			//TODO verify redirect uri if provided

			// do checking for different grant types
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
					GrantType.AUTHORIZATION_CODE.toString())) {
				if (!TestContent.AUTHORIZATION_CODE.equals(oauthRequest
						.getParam(OAuth.OAUTH_CODE))) {
					OAuthResponse response = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid authorization code")
							.buildJSONMessage();
					return Response.status(response.getResponseStatus())
							.entity(response.getBody()).build();
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
					GrantType.PASSWORD.toString())) {
				if (!TestContent.PASSWORD.equals(oauthRequest.getPassword())
						|| !TestContent.USERNAME.equals(oauthRequest
								.getUsername())) {
					OAuthResponse response = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid username or password")
							.buildJSONMessage();
					return Response.status(response.getResponseStatus())
							.entity(response.getBody()).build();
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
					GrantType.REFRESH_TOKEN.toString())) {
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_GRANT)
						.setErrorDescription("invalid username or password")
						.buildJSONMessage();
				return Response.status(response.getResponseStatus())
						.entity(response.getBody()).build();
			}

			OAuthResponse response = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(oauthIssuerImpl.accessToken())
					.setExpiresIn("3600").buildJSONMessage();

			return Response.status(response.getResponseStatus())
					.entity(response.getBody()).build();
		} catch (OAuthProblemException e) {
			OAuthResponse res = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
					.buildJSONMessage();
			return Response.status(res.getResponseStatus())
					.entity(res.getBody()).build();
		}
	}

	/*
	 * @GET
	 * 
	 * @Consumes("application/x-www-form-urlencoded")
	 * 
	 * @Produces("application/json") public Response authorizeGet(@Context
	 * HttpServletRequest request) throws OAuthSystemException { OAuthIssuer
	 * oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
	 * 
	 * OAuthResponse response = OAuthASResponse
	 * .tokenResponse(HttpServletResponse.SC_OK)
	 * .setAccessToken(oauthIssuerImpl.accessToken()) .setExpiresIn("3600")
	 * .buildJSONMessage();
	 * 
	 * return
	 * Response.status(response.getResponseStatus()).entity(response.getBody
	 * ()).build(); }
	 */
}