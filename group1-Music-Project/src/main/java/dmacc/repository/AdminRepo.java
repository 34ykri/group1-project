/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 15, 2023
 */
package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);
}
