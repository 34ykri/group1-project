package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.User;
import dmacc.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/Register")
	public String Register(Model model) {
		User u = new User();
		model.addAttribute("newUser", u);
		return "Register";
	}
	
	@PostMapping("/Register")
	public String Register(@ModelAttribute User u, Model model) {
		User u2 = userRepo.findByEmail(u.getEmail());
		if(u2 != null) {
			model.addAttribute("invalidEmail", true);
			model.addAttribute("newUser", u);
			return "Register";
		}
		if(!u.getPassword().equals(u.getConfirmPW())) {
			model.addAttribute("invalidPassword", true);
			model.addAttribute("newUser", u);
			return "Register";
		}
		userRepo.save(u);

		return "redirect:/Success";
	}
}
