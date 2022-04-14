package com.M2IProject.eventswipe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//lombok allow to not put getter and setter
@NoArgsConstructor
@AllArgsConstructor
//mark class as an Entity 
@Entity
//defining class name as Table name
@Table(name = "users")
public class UserEntity implements UserDetails {

    private static final long serialVersionUID = -1985551883116102444L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 200)
    private String first_name;

    @Column(length = 200)
    private String last_name;

    @Column(unique = true, nullable = false, length = 200)
    private String email;

    @Column(length = 200)
    private String city;

    @Column(nullable = false)
    private String password;

    @Column(name = "search_radius_km")
    private Integer searchRadiusKm;

    @Column(length = 30)
    private String gps_latitude;

    @Column(length = 30)
    private String gps_longitude;

    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<RoleEntity> roles;

    public UserEntity(String email, String password) {
	this.email = email;
	this.password = password;
    }

    public UserEntity(String email, String password, String first_name, String last_name, String city) {
	this.email = email;
	this.password = password;
	this.first_name = first_name;
	this.last_name = last_name;
	this.city = city;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().toString())));
	return authorities;
    }

    @Override
    public String getUsername() {
	return email;
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }

}
