package dmacc.repository;

import dmacc.beans.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query
    (value = "select * from shop b where b.brand like %:keyword% or b.item_name like %:keyword% or b.type like %:keyword%", nativeQuery = true)
    public List<Product> findByKeyword(@Param("keyword")String keyword);
}

