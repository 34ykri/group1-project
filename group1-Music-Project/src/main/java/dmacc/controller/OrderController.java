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
import dmacc.beans.User;
import dmacc.repository.CartRepository;
import dmacc.repository.OrderRepository;
import dmacc.repository.UserRepository;

@Controller
public class OrderController {
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	CartRepository cartRepo;
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/Checkout")
	public String Checkout(Model model) {
		Order o = new Order();
		User u = new User();
		o.setItems(cartRepo.findAll());
		o.calculateTotal();
		model.addAttribute("cart", cartRepo.findAll());
		model.addAttribute("newOrder", o);
		model.addAttribute("users", u);
		return "Checkout";
	}
	@PostMapping("/Checkout")
	public String Checkout(@ModelAttribute Order o, @ModelAttribute User u, Model model) {
		o.setOrderStatus("Processing");
		orderRepo.save(o);
		User u2 = userRepo.findByEmail(u.getEmail());
		if(u2 != null) {
			List<Order> userOrders = u2.getUserOrders();
			userOrders.add(o);
		}
		
		cartRepo.deleteAll();
		int id = o.getIdOrderNumber();
		return ReturnOrder(id, model);
	}
	
	@GetMapping("/OrderConfirmation/")
	public String ReturnOrder(int id, Model model) {
		Order o = orderRepo.findById(id).orElse(null);
		model.addAttribute("order", o);
		return "OrderConfirmation";
	}
	
	@GetMapping("OrderLookup/{id}")
	public String OrderLookup() {
		return null;
	}

}
