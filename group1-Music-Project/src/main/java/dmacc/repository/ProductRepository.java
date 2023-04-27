package dmacc.repository;

import dmacc.beans.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.brand LIKE %?1%" + "OR p.item LIKE %?1%" + "OR p.type LIKE %?1%" + "OR p.price LIKE %?1%")
    public List<Product> findAll(String keyword); 

}

