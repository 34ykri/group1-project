package dmacc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmacc.beans.CartEntity;
import dmacc.beans.Order;
import dmacc.beans.User;
import dmacc.repository.CartRepository;
import dmacc.repository.OrderRepository;
import dmacc.repository.ProductRepository;
import dmacc.repository.UserRepo;

@Controller
public class OrderController {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	CartRepository cartRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	OrderRepository orderRepo;
	
	@GetMapping("/ViewAdminOrders")
	public String ViewAdminOrders(Model model) {
		if(orderRepo.findAll().isEmpty()) {
			model.addAttribute("ordersEmpty", true);
			return "ViewAdminOrders";
		}
		model.addAttribute("ordersFull", true);
		model.addAttribute("admin", true);
		model.addAttribute("allOrders", orderRepo.findAll());
		return "OrderLookup";
	}
	@GetMapping("/AdminViewOrder/{id}")
	public String AdminViewOrder(@PathVariable("id") int id, Model model) {
		Order o = orderRepo.findById(id).orElse(null);
		if(o == null) {
			return "InvalidOrder";
		}
		Integer sessionId = id;
		List<CartEntity> orderList = cartRepo.findItems(sessionId.toString());
		if(orderList.isEmpty()) {
			System.out.println("empty");
		}
		model.addAttribute("order", o);
		model.addAttribute("items", orderList);
		return "AdminViewOrder";
	}
	
	//For now logging in will redirect to Order lookup until Authentication is figured out
		@GetMapping("/OrderLookup")
		public String OrderLookup(Model model) {
			User u = new User();
			model.addAttribute("login", true);
			model.addAttribute("returningUser", u);
			return "OrderLookup";
		}
		
		@PostMapping("/OrderLookup")
		public String OrderLookup(@ModelAttribute User u, Model model) {
			System.out.println("some");
			User u2 = userRepo.findByEmail(u.getEmail());
			if(u2 == null) {
				System.out.println("nulls");
				model.addAttribute("invalidUser", true);
				model.addAttribute("newUser", u);
				return "Register";
			}
//			if(!u.getPassword().equals(u2.getPassword())) {
//				model.addAttribute("returningUser", u);
//				model.addAttribute("invalidUser", true);
//				return "OrderLookup";
//			}
			System.out.println(u2.getId());
			if(orderRepo.findUserItems(u2.getId()).isEmpty()) {
				model.addAttribute("ordersEmpty", true);
				return "OrderLookup";
			}
			model.addAttribute("ordersFull", true);
			model.addAttribute("user", true);
			model.addAttribute("allOrders", orderRepo.findUserItems(u2.getId()));
			return "OrderLookup";
		}
		
		@GetMapping("/ViewOrder/{id}")
		public String ViewOrder(@PathVariable("id") int id, Model model) {
			Order o = orderRepo.findById(id).orElse(null);
			if(o == null) {
				return "InvalidOrder";
			}
			Integer sessionId = id;
			List<CartEntity> orderList = cartRepo.findItems(sessionId.toString());
			if(orderList.isEmpty()) {
				System.out.println("empty");
			}
			model.addAttribute("order", o);
			model.addAttribute("items", orderList);
			return "ViewOrder";
		}
}
