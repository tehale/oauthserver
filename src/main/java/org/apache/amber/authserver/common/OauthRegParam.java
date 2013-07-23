package org.apache.amber.authserver.common;

public class OauthRegParam {

	private String name ;
    private String redirectUrl;
    private String description = "Demo Application of the OAuth V2.0 Protocol";
    private String icon = "http://localhost:8080/demo.png";
	private String appUrl;
	
    public String getName() {
		return name;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public String getDescription() {
		return description;
	}
	public String getIcon() {
		return icon;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the appUrl
	 */
	public String getAppUrl() {
		return appUrl;
	}
	/**
	 * @param appUrl the appUrl to set
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
    
    
}
