package byAJ.Securex.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "USER_DATA")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="email", nullable=false)
    @Email(message="Please provide a valid email")
    @NotEmpty(message="Please provide an email")
    private String email;

    @Column(name="password")
    @NotEmpty(message="Please provide a password")
    private String password;

    @Column(name="first_name")
    @NotEmpty(message="Please provide a first name")
    private String firstName;

    @Column(name="last_name")
    @NotEmpty(message="Please provide a last name")
    private String lastName;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="username")
    @NotEmpty(message="Please provide a username")
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
//    private HashSet<Role> roles;

    public User() {
        roles = new HashSet<>();
    }


    // add a single role to this user
    public void addRole(Role role) {
        roles.add(role);
    }

    // remove a single role from this user
    public void removeRole(Role role) {
        roles.remove(role);
    }

    // add a bunch of roles (maybe from a checkbox)
    public void addRoles(HashSet<Role> roles) {
        this.roles.addAll(roles);
    }

    // remove a bunch of roles (maybe from a checkbox)
    public void removeRoles(HashSet<Role> roles) {
        this.roles.removeAll(roles);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }







//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }








    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }
}
