package dmacc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ContactController {
	
	@GetMapping("/get")
	public String contact(Model model) {
		model.addAttribute("userForm"); 
		return "ContactUs"; 
	}

}
