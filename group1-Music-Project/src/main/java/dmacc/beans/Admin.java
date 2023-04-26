/**
 * @author abhit - aryan9
 * CIS175 - Spring 2023
 * Apr 15, 2023
 */

package dmacc.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="admin")
public class Admin
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String password;
    
    @Column(nullable=false)
    private String role;
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
