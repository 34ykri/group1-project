package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dmacc.beans.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query(value="SELECT * FROM orders WHERE user_id = :id",  nativeQuery = true)
	public List<Order> findUserItems(@Param("id") int id);
}
