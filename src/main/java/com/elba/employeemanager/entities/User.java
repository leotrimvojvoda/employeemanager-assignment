package com.elba.employeemanager.entities;


import com.elba.employeemanager.enums.UserState;
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
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private UserState state = UserState.ACTIVE;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "role_id")
//    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User manager;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;

}
