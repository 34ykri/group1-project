package dmacc.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Product {

    @Column(name = "ID")
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "ITEM_NAME")
    private String item;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PHOTO_URL")
    private String photoURL;

    @Column(name = "INVENTORY")
    private int inventory;
}
