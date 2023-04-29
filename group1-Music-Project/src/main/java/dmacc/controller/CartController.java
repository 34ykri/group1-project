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
	@Autowired
	public AuthController authController;
	@Autowired
	public OrderController orderController;
	@Autowired
	public ProductController productController;
	String cartSessionId;

	@GetMapping("/ViewCart")
	public String ViewCart(Model model) {
		if(cartRepo.findAll().isEmpty()) {
			return "EmptyCart";
		}
		if(cartRepo.findItems(cartSessionId).isEmpty()) {
			return "EmptyCart";
		}
		
		double total = 0;
		List<CartEntity> cart = cartRepo.findItems(cartSessionId);
		
		for(int i = 0; i < cart.size(); i++) {
			String priceStr = cart.get(i).getPrice().replace("$", "");
			double price = Double.parseDouble(priceStr);
			price = price * cart.get(i).getQuantity();
			total = total + price;
		}
		
		total = Math.round(total*100)/100.0;
		model.addAttribute("total", total);
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
		CartEntity ce = cartRepo.findInCart(cartSessionId, id);
		if(ce == null) {
			ce =  new CartEntity(p.getId(), p.getBrand(), p.getItem(),p.getPrice());
			ce.setQuantity(1);
		}
		else {
			ce.setQuantity(ce.getQuantity()+1);
		}

		ce.setEntitySessionID(cartSessionId);

		if(ce == null || p == null) {
			return "products";
		}
		
		//Subtract from inventory
		cartRepo.save(ce);
		productRepo.save(p);
		model.addAttribute("addToCart", true);
		model.addAttribute("itemName", ce.getItem());
        model.addAttribute("products", productRepo.findAll());
		return "products";
	}
	@GetMapping("/DetailsAddToCart/{id}")
	public String DetailsAddToCart(@PathVariable("id") int id, Model model) {
	
		if(cartSessionId == null) {
			cartSessionId = CartSessionID.createCartSessionID();
		}
		Product p = productRepo.findById(id).orElse(null);
		//Id, brand, item, price
		CartEntity ce = cartRepo.findInCart(cartSessionId, id);
		if(ce == null) {
			ce =  new CartEntity(p.getId(), p.getBrand(), p.getItem(),p.getPrice());
			ce.setQuantity(1);
		}
		else {
			ce.setQuantity(ce.getQuantity()+1);
		}

		ce.setEntitySessionID(cartSessionId);

		if(ce == null || p == null) {
			return "products";
		}
		
		//Subtract from inventory
		cartRepo.save(ce);
		productRepo.save(p);
		model.addAttribute("addToCart", true);
		model.addAttribute("addedViewProduct", true);
        model.addAttribute("products", productRepo.findAll());
		return productController.ViewProduct(id, model);
	}
	@GetMapping("/AddOne/{id}")
	public String AddOne(@PathVariable("id") int id, Model model) {
	
		if(cartSessionId == null) {
			cartSessionId = CartSessionID.createCartSessionID();
		}
		Product p = productRepo.findById(id).orElse(null);
		//Id, brand, item, price
		CartEntity ce = cartRepo.findInCart(cartSessionId, id);
		if(ce == null) {
			ce =  new CartEntity(p.getId(), p.getBrand(), p.getItem(),p.getPrice());
			ce.setQuantity(1);
		}
		else {
			ce.setQuantity(ce.getQuantity()+1);
		}

		ce.setEntitySessionID(cartSessionId);

		if(ce == null || p == null) {
			return ViewCart(model);
		}
		
		//Subtract from inventory
		cartRepo.save(ce);
		productRepo.save(p);
		model.addAttribute("addToCart", true);
        model.addAttribute("products", productRepo.findAll());
		return ViewCart(model);
	}
	@GetMapping("/RemoveOne/{id}")
	public String RemoveOne(@PathVariable("id") int id, Model model) {
		if(cartSessionId == null) {
			cartSessionId = CartSessionID.createCartSessionID();
		}
		Product p = productRepo.findById(id).orElse(null);
		//Id, brand, item, price
		CartEntity ce = cartRepo.findInCart(cartSessionId, id);
		if(ce == null) {
			ce =  new CartEntity(p.getId(), p.getBrand(), p.getItem(),p.getPrice());
			ce.setQuantity(1);
			
		}
		else {
			ce.setQuantity(ce.getQuantity()-1);
		}

		ce.setEntitySessionID(cartSessionId);

		if(ce == null || p == null) {
			return ViewCart(model);
		}
		
		
		cartRepo.save(ce);
		if(ce.getQuantity() == 0) {
			cartRepo.delete(ce);
		}
		productRepo.save(p);
		model.addAttribute("addToCart", true);
        model.addAttribute("products", productRepo.findAll());
		return ViewCart(model);
	}
	@GetMapping("/RemoveFromCart/{id}")
	public String RemoveFromCart(@PathVariable("id") int id, Model model) {
		CartEntity ce = cartRepo.findById(id).orElse(null);
		
		Product p = productRepo.findById(ce.getProductId()).orElse(null);
		if(ce == null || p == null) {
			return "products";
		}
		//Re add inventory
		p.setInventory(p.getInventory() + ce.getQuantity());
		cartRepo.delete(ce);
		productRepo.save(p);
		return ViewCart(model);
	}
	@GetMapping("/ClearCart")
	public String ClearCart(Model model) {
		List<CartEntity> cartItems = cartRepo.findItems(cartSessionId);
		for(int i = 0; i<cartItems.size(); i++) {
			int id = cartItems.get(i).getId();
			int prodId = cartItems.get(i).getProductId();
			CartEntity ce = cartRepo.findById(id).orElse(null);
			Product p = productRepo.findById(prodId).orElse(null);
			p.setInventory(p.getInventory() + ce.getQuantity());
			cartRepo.delete(ce);
			productRepo.save(p);
		}
		return ViewCart(model);
	}
	@GetMapping("/Checkout")
	public String Checkout(Model model) {
		Order o = new Order();
		if(cartRepo.findItems(cartSessionId).isEmpty()) {
			return "EmptyCart";
		}
		o.setItems(cartRepo.findItems(cartSessionId));
		double tot = o.calculateTotal(cartRepo.findItems(cartSessionId));
		tot = tot + (tot * .06);
		tot = Math.round(tot*100)/100.0;
		o.setTotal(tot);
		model.addAttribute("cart", cartRepo.findItems(cartSessionId));
		model.addAttribute("newOrder", o);
		return "Checkout";
	}
	
	@PostMapping("/Checkout")
	public String Checkout(@RequestParam("month") String month, @RequestParam("year") String year, @ModelAttribute Order o, Model model) {
		User u = userRepo.findByEmail(o.getOrderEmail());
		double tot = o.calculateTotal(cartRepo.findItems(cartSessionId));
		
		tot = Math.round(tot*100)/100.0;
		o.setTotal(tot);
		o.setExpirationDate(month + "/" + year);


		if(u == null) {
			model.addAttribute("userError", true);
			model.addAttribute("cart", cartRepo.findItems(cartSessionId));
			model.addAttribute("newOrder", o);
			model.addAttribute("total", tot);
			return "Checkout";
		}
		
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
		//Subtract from Inventory
		for(int i = 0; i < cart.size(); i++) {
			CartEntity ce = cart.get(i);
			Product p = productRepo.findById(ce.getProductId()).orElse(null);
			if(p != null) {
				p.setInventory(p.getInventory()-ce.getQuantity());
				productRepo.save(p);
			}
			ce.setEntitySessionID(sessionId.toString());
			cartRepo.save(ce);
		}
		int id = o.getIdOrderNumber();
		return orderController.ViewOrder(id, model);
	}
	
	@GetMapping("/OrderConfirmation/")
	public String ReturnOrder(int id, Model model) {
		Order o = orderRepo.findById(id).orElse(null);
		model.addAttribute("order", o);
		return "OrderConfirmation";
	}
	
}
