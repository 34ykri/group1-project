package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dmacc.beans.CartEntity;
@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
	@Query(value="SELECT * FROM cart WHERE ENTITY_ID = :session AND product_id = :id LIMIT 1",  nativeQuery = true)
	public CartEntity findInCart(@Param("session") String session, @Param("id") int id);
	@Query(value="SELECT * FROM cart WHERE ENTITY_ID = :session",  nativeQuery = true)
	public List<CartEntity> findItems(@Param("session") String session);
	@Query(value="SELECT * FROM cart WHERE ID = :session",  nativeQuery = true)
	public List<CartEntity> findItemsId(@Param("session") String session);
}
