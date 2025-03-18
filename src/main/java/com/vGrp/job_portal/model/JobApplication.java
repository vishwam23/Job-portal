package com.vGrp.job_portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantName;
    private String email;
    private String resume; // Store resume text or URL

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
}
