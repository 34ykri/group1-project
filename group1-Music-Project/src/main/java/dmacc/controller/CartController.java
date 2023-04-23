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
import dmacc.beans.CartSessionID;
import dmacc.beans.Order;
import dmacc.beans.Product;
import dmacc.beans.User;
import dmacc.repository.CartRepository;
import dmacc.repository.OrderRepository;
import dmacc.repository.ProductRepository;
import dmacc.repository.UserRepo;

@Controller
public class CartController {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	CartRepository cartRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	OrderRepository orderRepo;
	
	String cartSessionId;

	@GetMapping("/ViewCart")
	public String ViewCart(Model model) {
		if(cartRepo.findAll().isEmpty()) {
			return "EmptyCart";
		}
		if(cartRepo.findItems(cartSessionId).isEmpty()) {
			return "EmptyCart";
		}
		model.addAttribute("cart", cartRepo.findItems(cartSessionId));
		return "Cart";
		
	}
	@GetMapping("/AddToCart/{id}")
	public String AddToCart(@PathVariable("id") int id, Model model) {

		if(cartSessionId == null) {
			cartSessionId = CartSessionID.createCartSessionID();
		}
		Product p = productRepo.findById(id).orElse(null);
		//Id, brand, item, price
		CartEntity ce =  new CartEntity(p.getId(), p.getBrand(), p.getItem(),p.getPrice());
		ce.setEntitySessionID(cartSessionId);

		if(ce == null || p == null) {
			return "AllProducts";
		}
		
		//Subtract from inventory
		ce.setQuantity(ce.getQuantity()+1);
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

	@GetMapping("/Checkout")
	public String Checkout(Model model) {
		Order o = new Order();
		o.setItems(cartRepo.findItems(cartSessionId));
		double tot = o.calculateTotal(cartRepo.findItems(cartSessionId));
		o.setTotal(tot);
		model.addAttribute("cart", cartRepo.findItems(cartSessionId));
		model.addAttribute("newOrder", o);
		return "Checkout";
	}
	
	@PostMapping("/Checkout")
	public String Checkout(@ModelAttribute Order o, Model model) {
		User u = userRepo.findByEmail(o.getOrderEmail());
		double tot = o.calculateTotal(cartRepo.findItems(cartSessionId));
		o.setTotal(tot);
		

		if(u == null) {
			User u2 = new User();
			model.addAttribute("invalidUser", true);
			model.addAttribute("newUser", u2);
			return "Register";
		}
//		if(!u.getPassword().equals(o.getPw())) {
//			model.addAttribute("userError", true);
//			model.addAttribute("cart", cartRepo.findItems(cartSessionId));
//			model.addAttribute("newOrder", o);
//			return "Checkout";
//		}
		List<CartEntity> orderCart = cartRepo.findItems(cartSessionId);
		List<CartEntity> cart = cartRepo.findItems(cartSessionId);
		if(orderCart.isEmpty()) {
			return "EmptyCart";		
		}
		
		o.setItems(orderCart);
		o.setOrderStatus("Processing");
		o.setUserId(u.getId());
		userRepo.save(u);
		o.setPw("");
		orderRepo.save(o);
		Integer sessionId = o.getIdOrderNumber();
		//System.out.println(sessionId);
		//Subtract from Inventory
		for(int i = 0; i < cart.size(); i++) {
			CartEntity ce = cart.get(i);
			Product p = productRepo.findById(ce.getProductId()).orElse(null);
			if(p != null) {
				p.setInventory(p.getInventory()-ce.getQuantity());
				productRepo.save(p);
			}
			ce.setEntitySessionID(sessionId.toString());
			//System.out.println("esid " + ce.getEntitySessionID());
			cartRepo.save(ce);
		}
		int id = o.getIdOrderNumber();
		return ReturnOrder(id, model);
	}
	
	@GetMapping("/OrderConfirmation/")
	public String ReturnOrder(int id, Model model) {
		Order o = orderRepo.findById(id).orElse(null);
		model.addAttribute("order", o);
		return "OrderConfirmation";
	}
	
}
