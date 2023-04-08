package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.Cart;
import dmacc.beans.Product;
@Repository
public interface CartRepository extends JpaRepository<Product, Integer> {

}
