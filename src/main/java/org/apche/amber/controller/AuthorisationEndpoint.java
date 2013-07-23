package org.apche.amber.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.OAuthAuthzRequest;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/oauth")
public class AuthorisationEndpoint {

	private static final Logger logger = Logger
			.getLogger(AuthorisationEndpoint.class);

	@RequestMapping(value = "/authorise", method = RequestMethod.GET)
	public Response welcome(HttpServletRequest request)
			throws URISyntaxException, OAuthSystemException {
		boolean isUserAuthenticated = false;
		if (logger.isInfoEnabled()) {

			logger.info("Authorise request received: " + request);
		}
		HttpSession session = request.getSession(false);
		if (session == null) {
			logger.debug("New user session has been created now.");
		} else if (session.getAttribute("authenticated") != null ) {
			logger.debug("User is authenticated");
			isUserAuthenticated = (Boolean) session
					.getAttribute("authenticated");
		}
		if (!isUserAuthenticated) {
			logger.debug("User authentication value is false");
			// Redirect of authentication
			// return "redirect:/userlogin";
		} else {
			// Ask user consent

		}
		OAuthAuthzRequest oauthRequest = null;

		OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(
				new MD5Generator());
		try {

			oauthRequest = new OAuthAuthzRequest(request);
			
			// TODO build response according to response_type
			String responseType = oauthRequest
					.getParam(OAuth.OAUTH_RESPONSE_TYPE);

			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
					.authorizationResponse(request,
							HttpServletResponse.SC_FOUND);

			if (responseType.equals(ResponseType.CODE.toString())) {
				logger.debug("authorisation code is :"
						+ oauthIssuerImpl.authorizationCode());
				builder.setCode(oauthIssuerImpl.authorizationCode());
			}
			// For implicit application token is sent in first request only.
			if (responseType.equals(ResponseType.TOKEN.toString())) {
				builder.setAccessToken(oauthIssuerImpl.accessToken());
				builder.setExpiresIn(3600l);
			}

			String redirectURI = oauthRequest
					.getParam(OAuth.OAUTH_REDIRECT_URI);
			// verify redirect uri.
			final OAuthResponse response = builder.location(redirectURI)
					.buildQueryMessage();
			URI url = new URI(response.getLocationUri());
			return Response.status(response.getResponseStatus()).location(url)
					.build();

			
		} catch (OAuthProblemException e) {

			final Response.ResponseBuilder responseBuilder = Response
					.status(HttpServletResponse.SC_FOUND);

			String redirectUri = e.getRedirectUri();

			if (OAuthUtils.isEmpty(redirectUri)) {
				throw new WebApplicationException(responseBuilder.entity(
						"OAuth callback url needs to be provided by client!!!")
						.build());
			}
			final OAuthResponse response = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			final URI location = new URI(response.getLocationUri());
			return responseBuilder.location(location).build();
		}
	}
}
