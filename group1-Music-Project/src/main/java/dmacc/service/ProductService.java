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
		return repo.findAll();
	}
	
	public void save(Product product) {
		repo.save(product); 
	}
	
//	 public List<Product> getAllProducts(String keyword){
//		 if(keyword != null) {
//			 return repo.findByKeyword(keyword);	
//		 }
//		  List<Product> list =  (List<Product>)repo.findAll();
//		  return list;
//		 }
//		 
//		 public List<Product> getByKeyword(String keyword){
//		  return repo.findByKeyword(keyword);
//		 }
	}

