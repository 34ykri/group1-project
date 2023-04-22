package dmacc.beans;

import java.util.UUID;

import lombok.Data;
@Data
public class CartSessionID {
	
	//This will generate a random id for the cart
	//So it will only show the user's items in their cart	
	public static String createCartSessionID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
