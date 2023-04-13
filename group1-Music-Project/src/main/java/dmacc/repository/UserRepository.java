package dmacc.repository;

import dmacc.beans.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value="SELECT u FROM users u WHERE u.email = ?",  nativeQuery = true)
	User findByEmail(String email);
}
