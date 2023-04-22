/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 11, 2023
 */
package dmacc.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dmacc.beans.Admin;
import dmacc.beans.Role;
import dmacc.beans.User;
import dmacc.dto.UserDto;
import dmacc.repository.AdminRepo;
import dmacc.repository.RoleRepo;
import dmacc.repository.UserRepo;
import dmacc.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepository;
    private RoleRepo roleRepository;
    private AdminRepo adminRepository;
    private PasswordEncoder passwordEncoder;
    

    public UserServiceImpl(UserRepo userRepository, RoleRepo roleRepository, AdminRepo adminRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public Admin findAdminByUsername(String username) {
    	return adminRepository.findByUsername(username);
    }
   
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }
    
    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
