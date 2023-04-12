package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dmacc.beans.CartEntity;
@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
	@Query(value="SELECT * FROM cart WHERE ENTITY_ID = ?",  nativeQuery = true)
	public List<CartEntity> findItems(String session);
}
