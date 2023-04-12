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
import dmacc.repository.OrderRepository;

@Controller
public class OrderController {
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	CartRepository cartRepo;
	@GetMapping("/Checkout")
	public String Checkout(Model model) {
		Order o = new Order();
		o.setItems(cartRepo.findAll());
		o.calculateTotal();
		model.addAttribute("cart", cartRepo.findAll());
		model.addAttribute("newOrder", o);
		return "Checkout";
	}
	@PostMapping("/Checkout")
	public String Checkout(@ModelAttribute Order o, Model model) {
		o.setOrderStatus("Processing");
		orderRepo.save(o);
		cartRepo.deleteAll();
		int id = o.getIdOrderNumber();
		return returnOrder(id, model);
	}
	
	@GetMapping("/OrderConfirmation/")
	public String returnOrder(int id, Model model) {
		Order o = orderRepo.findById(id).orElse(null);
		model.addAttribute("order", o);
		return "OrderConfirmation";
	}


}
