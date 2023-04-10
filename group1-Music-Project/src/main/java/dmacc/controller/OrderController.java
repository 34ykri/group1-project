package dmacc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.CartEntity;
import dmacc.beans.Order;
import dmacc.repository.CartRepository;

@Controller
public class OrderController {
	@Autowired
	CartRepository cartRepo;
	@GetMapping("/Checkout")
	public String Checkout(Model model) {
		Order o = new Order();
		o.setItems(cartRepo.findAll());
		o.calculateTotal();
		model.addAttribute("newOrder", o);
		model.addAttribute("cart", cartRepo.findAll());
		return "Checkout";
	}
	@PostMapping("/Checkout/{id}")
	public String Checkout(@PathVariable("id") int id, @ModelAttribute Order o, Model model) {
		
		return returnOrder(id, model);
	}
	
	@GetMapping("/OrderConfirmation/{id}")
	public String returnOrder(@PathVariable("id") int id, Model model) {
		
		return "OrderConfirmation";
	}

}
