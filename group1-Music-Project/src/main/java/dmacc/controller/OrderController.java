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
import dmacc.repository.ProductRepository;
import dmacc.repository.UserRepo;
import dmacc.service.UserService;

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
	@Autowired
    private UserService userService;
	@Autowired
	public AuthController authController;

	@GetMapping("/ViewAdminOrders")
	public String ViewAdminOrders(Model model) {
		if(orderRepo.findAll().isEmpty()) {
			model.addAttribute("admin", true);
			model.addAttribute("ordersEmpty", true);
			return "OrderLookup";
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
	@GetMapping("/SetOrder/{id}/{status}")
	public String SetOrder(@PathVariable("id") int orderId, @PathVariable("status") String status, Model model) {
		Order o = orderRepo.findById(orderId).orElse(null);
		o.setOrderStatus(status);
		Integer sessionId = orderId;
		List<CartEntity> orderList = cartRepo.findItems(sessionId.toString());
		model.addAttribute("order", o);
		model.addAttribute("items", orderList);
		orderRepo.save(o);
		return AdminViewOrder(orderId, model);
	}
	//For now logging in will redirect to Order lookup until Authentication is figured out
		@GetMapping("/OrderLookup")
		public String OrderLookup(Model model) {
			User u = new User();
			model.addAttribute("user", true);
			model.addAttribute("login", true);
			model.addAttribute("returningUser", u);
			return "OrderLookup";
		}
		
		@PostMapping("/OrderLookup")
		public String OrderLookup(@ModelAttribute User u, Model model) {
			User u2 = userRepo.findByEmail(u.getEmail());
			if(u2 == null) {
				model.addAttribute("invalidUser", true);
				model.addAttribute("login", true);
				return OrderLookup(model);

			}
			if(orderRepo.findUserItems(u2.getId()).isEmpty()) {
				model.addAttribute("ordersEmpty", true);
				return "OrderLookup";
			}
			model.addAttribute("ordersFull", true);
			model.addAttribute("user", true);
			model.addAttribute("allOrders", orderRepo.findUserItems(u2.getId()));
			return "OrderLookup";
		}
		@GetMapping("/OrderLookup/{userId}")
		public String OrderLookup(@PathVariable("userId") int userId, Model model) {
			User u2 = userRepo.findId(userId);
			if(u2 == null) {
				return authController.showRegistrationForm(model);
			}
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
