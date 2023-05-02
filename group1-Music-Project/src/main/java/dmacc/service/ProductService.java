package dmacc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmacc.beans.Product;
import dmacc.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo; 
	
	public List<Product> listAll(String keyword) {
		if(keyword != null) {
			
		}
		return repo.findAll(keyword);
	}
	
	public void save(Product product) {
		repo.save(product); 
	}

}

