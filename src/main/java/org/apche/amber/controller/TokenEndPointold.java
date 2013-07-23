package org.apche.amber.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.amber.authserver.common.Common;
import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuer;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.OAuthTokenRequest;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TokenEndPointold {
	
	@RequestMapping(value="/token",method=RequestMethod.GET)
	 public Response authorize(HttpServletRequest request) throws OAuthSystemException {
    OAuthTokenRequest oauthRequest = null;

    OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

    try {
        oauthRequest = new OAuthTokenRequest(request);
        
        //check if client-id is valid
        if (!Common.CLIENT_ID.equals(oauthRequest.getParam(OAuth.OAUTH_CLIENT_ID))) {
            OAuthResponse response =
                OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("client_id not found")
                    .buildJSONMessage();
            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
        }

        //do checking for different grant types
        if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
            .equals(GrantType.AUTHORIZATION_CODE.toString())) {
            if (!Common.AUTHORIZATION_CODE.equals(oauthRequest.getParam(OAuth.OAUTH_CODE))) {
                OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_GRANT)
                    .setErrorDescription("invalid authorization code")
                    .buildJSONMessage();
                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
            }
        } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
            .equals(GrantType.PASSWORD.toString())) {
            if (!Common.PASSWORD.equals(oauthRequest.getPassword())
                || !Common.USERNAME.equals(oauthRequest.getUsername())) {
                OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_GRANT)
                    .setErrorDescription("invalid username or password")
                    .buildJSONMessage();
                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
            }
        } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE)
            .equals(GrantType.REFRESH_TOKEN.toString())) {
            OAuthResponse response = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("invalid username or password")
                .buildJSONMessage();
            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
        }

        OAuthResponse response = OAuthASResponse
            .tokenResponse(HttpServletResponse.SC_OK)
            .setAccessToken(oauthIssuerImpl.accessToken())
            .setExpiresIn("3600")
            .buildJSONMessage();

        return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
    } catch (OAuthProblemException e) {
        OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
            .buildJSONMessage();
        return Response.status(res.getResponseStatus()).entity(res.getBody()).build();
    }
}
}

