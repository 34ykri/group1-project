package dmacc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Product;
import dmacc.service.ProductService;

@Controller
public class SearchController {
	
	@Autowired
	private ProductService service;

	
	@GetMapping("/search")
	public String newSearch(Model model) {
		Product product = new Product(); 
		model.addAttribute("product", product);
		
		return "search"; 
	}
	
	@PostMapping("/search")
	public String viewSearch(Model model, @Param("keyword") String keyword) {
		List<Product> listProducts = service.listAll(keyword);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("keyword", keyword);
		
		return "search"; 
		
	}
	
}
