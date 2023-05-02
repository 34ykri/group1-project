package dmacc.beans;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue
	private int idOrderNumber;
	private int userId;
	
	private String fname;
	private String lname;
	private String orderEmail;
	
	private String address;
	private String zip;
	private String state;
	private String city;
	
	private String cardNumber;
	private String securityCode;
	private String nameOnCard;
	private String expirationDate;
	
	private double total;
	private String orderStatus;
	private String pw;
	
	@OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY, mappedBy="order")
	public List<CartEntity> items;
	
	public double calculateTotal(List<CartEntity> theseItems) {
		double calTotal = 0;
		
		for(int i = 0; i < theseItems.size(); i++) {
			CartEntity thisItem = theseItems.get(i);

			String formattedPrice = thisItem.getPrice().replace("$", "");
			double itemPrice =  Double.parseDouble(formattedPrice);
			double itemTotalPrice = itemPrice * thisItem.getQuantity();
			calTotal = calTotal + itemTotalPrice;
		}
		return calTotal;
	}
}
