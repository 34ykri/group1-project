package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dmacc.beans.CartEntity;
import dmacc.beans.Product;
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
		//Id, brand, item, price
		CartEntity ce = cartRepo.findById(id).orElse(null);
		if(ce == null) {
			ce = new CartEntity(p.getId(), p.getBrand(), p.getItem(),p.getPrice());
		}
		if(ce == null || p == null) {
			return "AllProducts";
		}
		/*
		if(p.getInventory() == 0) {
			return "AllProducts";
		}
		*/
		//Subtract from inventory
		ce.setQuantity(ce.getQuantity()+1);
		p.setInventory(p.getInventory() - 1);
		cartRepo.save(ce);
		productRepo.save(p);
		return ViewCart(model);
	}
	@GetMapping("/RemoveFromCart/{id}")
	public String RemoveFromCart(@PathVariable("id") int id, Model model) {
		CartEntity ce = cartRepo.findById(id).orElse(null);
		Product p = productRepo.findById(id).orElse(null);
		if(ce == null || p == null) {
			return "AllProducts";
		}
		//Re add inventory
		p.setInventory(p.getInventory() + ce.getQuantity());
		cartRepo.delete(ce);
		productRepo.save(p);
		return "Cart";
	}
	
	/*
	@GetMapping("/AddToCart/{user}/{id}")
	public String AddToCart(@PathVariable("id") int id, @PathVariable("user") User user, Model model ) {
		return null;
	}
	*/
	
	
}
