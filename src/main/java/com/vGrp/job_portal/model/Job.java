package com.vGrp.job_portal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.ui.Model;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;
    private String description;

    @ManyToOne
    private User employer;  // Job is posted by an Employer

    // Getters & Setters
}
