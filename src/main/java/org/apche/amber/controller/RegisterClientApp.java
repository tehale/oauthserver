/**
 * 
 */
package org.apche.amber.controller;

import org.apache.amber.authserver.Dao.RegisterAppDao;
import org.apache.amber.authserver.common.OauthRegParam;
import org.apache.amber.authserver.service.RegisterAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ankush
 * 
 */
@Controller
@RequestMapping("/register")
public class RegisterClientApp {

	@Autowired
	public RegisterAppDao registerAppDao;
	
	@Autowired
	public RegisterAppService registerAppService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registerApp() {
		return new ModelAndView("register", "clientApp", new OauthRegParam());
	}
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public Object SaveClientApp(@ModelAttribute("clientApp") OauthRegParam clientApp){
		
		//validate client app.
		return registerAppService.saveClientApp(clientApp);
		
		}
}
