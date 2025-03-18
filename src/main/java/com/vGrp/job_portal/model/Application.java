package com.vGrp.job_portal.model;

import jakarta.persistence.*;
import org.springframework.ui.Model;

import lombok.*;


@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User jobSeeker;

    @ManyToOne
    private Job job;

    private String resumeUrl;
    private String status; // Applied, Shortlisted, Hired

    // Getters & Setters
}
