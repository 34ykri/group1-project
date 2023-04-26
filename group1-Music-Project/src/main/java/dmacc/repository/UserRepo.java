/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 11, 2023
 */
package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dmacc.beans.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u WHERE u.id = :id")
   	User findId(@Param("id") int id);
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
	User login(@Param("email") String email, @Param("password") String password);
}
