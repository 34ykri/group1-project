package dmacc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Contact;


@Controller
public class ContactController {
	
	@GetMapping("/ContactUs")
	public String contact(Model model) {
		model.addAttribute("contact", new Contact());
		
		return "ContactUs"; 
	}
	
	@PostMapping("/ContactUs")
	public String contactForm(@ModelAttribute Contact contact, Model model) {
		model.addAttribute("contact", new Contact());
		model.addAttribute("submitMessage", "Thank you for submitting your questions and comments!");
		return "ContactUs";
	}

}
