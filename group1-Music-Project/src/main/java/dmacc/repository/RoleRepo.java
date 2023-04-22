/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 11, 2023
 */
package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
