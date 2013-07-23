/**
 * 
 */
package org.apache.amber.authserver.validate;

import org.apache.amber.authserver.common.User;
import org.springframework.stereotype.Component;

/**
 * @author ankush
 *
 */
@Component
public class CommonValidation {

	
	public boolean validateUser(User user){
		
		if(null ==user){return false;}
		if(user.getUserId().isEmpty()||user.getPassword().isEmpty()){
			return false;
		
		}else {
			return true;
			
		}
	}
	
}
