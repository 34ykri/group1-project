package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.repository.ProductRepository;


public class SearchController {
	
	@Autowired
	ProductRepository productRepo;
	
	@GetMapping("/ViewItems")
	public String ViewItems(Model model) {
		if(productRepo.findAll().isEmpty()) {
			return "search";
		}
		model.addAttribute("saved", productRepo.findAll());
		return "SavedItems";
	}
	
	@PostMapping("/")
	public String ViewItems() {
		return "SavedItems"; 
	}

}
