package dmacc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dmacc.beans.CartEntity;
import dmacc.beans.Order;
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
		model.addAttribute("allOrders", orderRepo.findAll());
		return "ViewAdminOrders";
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
}
