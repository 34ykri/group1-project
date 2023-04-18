package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dmacc.beans.Product;
import dmacc.beans.SavedEntity;
import dmacc.repository.ProductRepository;
import dmacc.repository.SavedRepository;

/**
 * @author stephaniesink - sisink
 * CIS175 - Spring 2022
 * Apr 09, 2023
 */
@Controller
public class SavedController {
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	SavedRepository saveRepo;
	
	@GetMapping("/ViewSavedItems")
	public String ViewSavedItems(Model model) {
		if(saveRepo.findAll().isEmpty()) {
			return "AllProducts";
		}
		model.addAttribute("saved", saveRepo.findAll());
		return "SavedItems";
	}
	
	@GetMapping("/SaveForLater/{id}")
	public String SaveForLater(@PathVariable("id") int id, Model model) {
		Product p = productRepo.findById(id).orElse(null);
		SavedEntity se = saveRepo.findById(id).orElse(null);
		if(se == null) {
			se = new SavedEntity(p.getId(), p.getBrand(), p.getItem(),p.getPrice());
		}
		if(se == null || p == null) {
			return "AllProducts";
		}
		saveRepo.save(se);
		productRepo.save(p);
		return ViewSavedItems(model);
	}
	
	@GetMapping("/RemoveFromSaved/{id}")
	public String RemoveFromSaved(@PathVariable("id") int id, Model model) {
		SavedEntity se = saveRepo.findById(id).orElse(null);
		Product p = productRepo.findById(id).orElse(null);
		if(se == null || p == null) {
			return "AllProducts";
		}
		saveRepo.delete(se);
		productRepo.save(p);
		return "SavedItems";
	}
	
}
