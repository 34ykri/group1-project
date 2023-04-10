package dmacc.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue
	private int idOrderNumber;
	//First, last , Email are placeholders until we can get user working
	private String fname;
	private String lname;
	private String orderEmail;
	
	private int cardNumber;
	private int securityCode;
	private String nameOnCard;
	private String expirationDate;
	
	private double total;
	private String orderStatus;
	
	@OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	private List<CartEntity> items;
	
	public void calculateTotal() {
		for(int i = 0; i < items.size(); i++) {
			CartEntity thisItem = items.get(i);
			double itemPrice = Double.parseDouble(thisItem.getPrice());
			this.total = this.total + itemPrice;
		}
	}
	
}
