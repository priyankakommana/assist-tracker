package com.project.assist_tracker.model;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user") // IMPORTANT - User is reserved word
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    private String password;
    private String role;
    
    public User() {}
    public User(String u, String p, String r){ 
        this.username=u.toLowerCase(); 
        this.password=p; 
        this.role=r; 
    }
    
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getUsername(){return username;} public void setUsername(String u){this.username=u.toLowerCase();}
    public String getPassword(){return password;} public void setPassword(String p){this.password=p;}
    public String getRole(){return role;} public void setRole(String r){this.role=r;}
}