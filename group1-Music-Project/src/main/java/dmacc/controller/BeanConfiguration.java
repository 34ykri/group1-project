package dmacc.controller;

import dmacc.beans.CartEntity;
import dmacc.beans.Order;
import dmacc.beans.Product;
import dmacc.beans.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    Product product() {
        Product product = new Product();
        return product;
    }

    @Bean
    User user() {
        User user = new User();
        return user;
    }
    @Bean
    CartEntity cartEntity() {
    	CartEntity cartEntity = new CartEntity();
    	return cartEntity;
    }
    @Bean
    Order order() {
    	Order order = new Order();
    	return order;
    }
    
}
