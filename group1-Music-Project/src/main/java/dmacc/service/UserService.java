/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 11, 2023
 */
package dmacc.service;

import java.util.List;

import dmacc.beans.Admin;
import dmacc.beans.User;
import dmacc.dto.UserDto;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
    
    List<UserDto> findAllUsers();
    
	Admin findAdminByUsername(String username);
}
