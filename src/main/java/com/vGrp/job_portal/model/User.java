package com.vGrp.job_portal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.ui.Model;

import org.springframework.aot.generate.GeneratedMethod;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role; // CLIENT or EMPLOYER
//    private String username;
//    private String password;
//    private String role;

}
