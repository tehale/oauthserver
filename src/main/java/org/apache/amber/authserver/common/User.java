/**
 * 
 */
package org.apache.amber.authserver.common;

/**
 * @author ankush
 *
 */
public class User {

	String userId;
	String password;
	
	
	public String getUserId() {
		return userId;
	}
	public String getPassword() {
		return password;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
