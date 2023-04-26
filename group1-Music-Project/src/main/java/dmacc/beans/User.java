package dmacc.beans;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false)
    private int id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;
    
    @Column(nullable=false)
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();
    
//    @Column(name = "USER_ORDERS")
//	  @OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
//    private List<Order> userOrders;
    
    
//    @OneToMany(mappedBy = "User")
//    private List<Product> cart = new ArrayList<Product>();
}
