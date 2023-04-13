package dmacc.beans;


import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Column(name = "ID")
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "USER_ORDERS")
	@OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
    private List<Order> userOrders;
    
    
//    @OneToMany(mappedBy = "User")
//    private List<Product> cart = new ArrayList<Product>();
}
