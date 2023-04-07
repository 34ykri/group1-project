package dmacc.beans;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
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

//    @OneToMany(mappedBy = "User")
//    private List<Product> cart = new ArrayList<Product>();
}
