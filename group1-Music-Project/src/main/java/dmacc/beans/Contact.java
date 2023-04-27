package dmacc.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Contact {
	@Id
	@GeneratedValue
	private String firstName; 
	private String lastName;
	private String email; 
	private String message; 
	

}
