package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.CartEntity;
@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

}
