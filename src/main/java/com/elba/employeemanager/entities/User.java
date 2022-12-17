package com.elba.employeemanager.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lasstName;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;

}
