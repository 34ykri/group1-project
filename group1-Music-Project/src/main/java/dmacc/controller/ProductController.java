package dmacc.controller;

import dmacc.beans.Product;
import dmacc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepo;
    @Autowired
    AuthController authController;
    
    @GetMapping("/Success")
    public String Success() {
        return "redirect:/";
    }

    @GetMapping("/AddProduct") //Currently used to test adding products
    public String AddProduct(Model model) {
        Product p = new Product();
        model.addAttribute("add", true);
        model.addAttribute("NewProduct", p);
        return "AddProduct";
    }

    @PostMapping("/AddProduct")
    public String AddProduct(@ModelAttribute Product p, Model model) {
        productRepo.save(p);
        return "redirect:/AddProduct?success";
    }
    @GetMapping("/EditProduct/{id}")
    public String EditProduct(@PathVariable("id") int id, Model model) {
        Product p = productRepo.findById(id).orElse(null);
        model.addAttribute("edit", true);
        model.addAttribute("editProduct", p);
        return "AddProduct";
    }
    @PostMapping("/EditProduct")
    public String EditProduct(@ModelAttribute Product p, Model model) {
    	productRepo.save(p);
        return authController.AdminShowProductsForm(model);
    }
    @GetMapping("/DeleteProduct/{id}")
    public String DeleteProduct(@PathVariable("id") int id, Model model) {
        Product p = productRepo.findById(id).orElse(null);
        model.addAttribute("delete", true);
        model.addAttribute("deleteProduct", p);
        return "AddProduct";
    }
    @PostMapping("/DeleteProduct")
    public String DeleteProduct(@ModelAttribute Product p, Model model) {
    	productRepo.delete(p);
        return authController.AdminShowProductsForm(model);
    }
    @GetMapping("ViewAllProducts")
    public String ViewAllProducts(Model model) {
        if(productRepo.findAll().isEmpty()) {
            return "products";
        }

        model.addAttribute("products", productRepo.findAll());

        return "ViewAllProducts";
    }
}
