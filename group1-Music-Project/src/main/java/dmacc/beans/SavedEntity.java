package dmacc.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stephaniesink - sisink
 * CIS175 - Spring 2022
 * Apr 09, 2023
 */

@Data
@NoArgsConstructor
@Entity
public class SavedEntity {
	
    @Column(name = "ID")
    @Id
    private int id;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "ITEM_NAME")
    private String item;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "PHOTO_URL")
    private String photoURL;
    
    public SavedEntity(int id, String brand, String item, String price) {
    	this.id = id;
    	this.brand = brand;
    	this.item = item;
    	this.price = price;
    }

}
