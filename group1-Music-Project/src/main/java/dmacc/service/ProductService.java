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
	
	 public List<Product> getAllProducts(){
		  List<Product> list =  (List<Product>)repo.findAll();
		  return list;
		 }
		 
		 public List<Product> getByKeyword(String keyword){
		  return repo.findByKeyword(keyword);
		 }
		}

