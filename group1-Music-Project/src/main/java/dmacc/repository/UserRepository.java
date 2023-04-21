package dmacc.repository;

import dmacc.beans.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
	User login(@Param("email") String email, @Param("password") String password);
}*/
