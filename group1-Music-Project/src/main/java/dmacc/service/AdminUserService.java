/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 18, 2023
 */
package dmacc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dmacc.beans.Admin;
import dmacc.repository.AdminRepo;

@Service
public class AdminUserService {
	
	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	 public void createPresetAdminUser() {
		 
		 if(adminRepo.findByUsername("admin") == null) {
			 
			 Admin admin = new Admin();
			 admin.setUsername("admin");
			 admin.setPassword(passwordEncoder.encode("groupOne"));
			 admin.setRole("ROLE_ADMIN");
			 adminRepo.save(admin);
	     }
	 }
	 
	 public boolean checkPresetAdminCredentials(String username, String password) {

	     Admin admin = adminRepo.findByUsername(username);
	     if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {

	    	 return true;
	     }
	     return false;
	 }
}
