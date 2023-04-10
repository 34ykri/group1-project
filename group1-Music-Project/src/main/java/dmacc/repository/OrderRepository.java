package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.beans.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
