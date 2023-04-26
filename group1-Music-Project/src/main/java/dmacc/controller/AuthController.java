/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 11, 2023
 */
package dmacc.controller;

import dmacc.repository.ProductRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import dmacc.beans.User;
import dmacc.dto.UserDto;
import dmacc.service.AdminUserService;
import dmacc.service.UserService;

import java.util.List;

@Controller
public class AuthController {

	@Autowired
    private UserService userService;
    
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProductRepository productRepo;

    public AuthController(UserService userService, AdminUserService adminUserService) {
        this.userService = userService;
        this.adminUserService = adminUserService;
    }
    
    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
    
    @GetMapping("products")
    public String showProductsForm(Model model) {
    	UserDto user = new UserDto();
    	model.addAttribute("user", user);
        model.addAttribute("products", productRepo.findAll());
        return "products";
    }
    
    @GetMapping("AdminProducts")
    public String AdminShowProductsForm(Model model) {
    	UserDto user = new UserDto();
    	model.addAttribute("user", user);
        model.addAttribute("products", productRepo.findAll());
        return "AdminProducts";
    }
    
    @GetMapping("adminLogin")
    public String adminLogin() {
        adminUserService.createPresetAdminUser();
        return "adminLogin";
    }
    
    @PostMapping("/adminLogin")
    public String adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
    	
        if (adminUserService.checkPresetAdminCredentials(username, password)) {
        	List<UserDto> users = userService.findAllUsers();
            model.addAttribute("users", users);
            return "users";
        }
        return "redirect:/adminLogin?error=true";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save") 
    public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
    	
        User existing = userService.findUserByEmail(user.getEmail());
        
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}