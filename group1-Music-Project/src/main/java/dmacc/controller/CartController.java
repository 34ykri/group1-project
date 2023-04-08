package dmacc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dmacc.beans.Product;
import dmacc.beans.User;
import dmacc.repository.CartRepository;
import dmacc.repository.ProductRepository;

@Controller
public class CartController {
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	@GetMapping("/ViewCart")
	public String ViewCart(Model model) {
		if(cartRepo.findAll().isEmpty()) {
			return "EmptyCart";
		}
		model.addAttribute("cart", cartRepo.findAll());
		return "Cart";
	}
	@GetMapping("/AddToCart/{id}")
	public String AddToCart(@PathVariable("id") int id, Model model) {
		Product p = productRepo.findById(id).orElse(null);
		if(p == null) {
			return "AllProducts";
		}
		cartRepo.save(p);
		return "Cart";
	}
	@GetMapping("/RemoveFromCart/{id}")
	public String RemoveFromCart(@PathVariable("id") int id, Model model) {
		Product p = productRepo.findById(id).orElse(null);
		if(p == null) {
			return "AllProducts";
		}
		cartRepo.delete(p);
		return "Cart";
	}
	
	/*
	@GetMapping("/AddToCart/{user}/{id}")
	public String AddToCart(@PathVariable("id") int id, @PathVariable("user") User user, Model model ) {
		return null;
	}
	*/
	
	
}
