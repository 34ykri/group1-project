package dmacc.controller;

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

}
