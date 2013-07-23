/**
 * 
 */
package org.apche.amber.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.amber.authserver.common.User;
import org.apache.amber.authserver.service.AuthorisationService;
import org.apache.amber.authserver.validate.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author ankush
 *
 */
@Controller
@RequestMapping("/userlogin")
@SessionAttributes("userAuthenticated")
public class UserLogin {
	
	@Autowired
	public AuthorisationService authorisationService;
	
	@Autowired
	public CommonValidation commonValidation;
	@RequestMapping(method=RequestMethod.GET)
	public String getLoginForm(ModelMap map){
		map.addAttribute("user", new User());
		return "newlogin";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String postLoginForm(@ModelAttribute("user") User user,HttpServletRequest request){
			
	if(commonValidation.validateUser(user) && authorisationService.authoriseUser(user)){
		HttpSession session=request.getSession();
		session.setAttribute("authenticated",true);
		return "redirect:/authorise";
		}
	else{
		ModelMap map =new ModelMap();
		map.addAttribute("error", "user credential are invalid");
		return "newlogin";
	}
	
	}	

}
