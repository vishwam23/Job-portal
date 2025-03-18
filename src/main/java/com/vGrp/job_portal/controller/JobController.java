package com.vGrp.job_portal.controller;

import com.vGrp.job_portal.model.Job;
import com.vGrp.job_portal.model.JobApplication;
import com.vGrp.job_portal.repository.JobApplicationRepository;
import com.vGrp.job_portal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private JobApplicationRepository jobAppRepo;

    // List all jobs
    @GetMapping("/")
    public String listJobs(ModelMap model) {
        List<Job> jobs = jobRepo.findAll();
        model.addAttribute("jobs", jobs);
        return "jobs";
    }

    // Show job posting form (Only accessible if logged in)
    @GetMapping("/post")
    public String showPostJobForm(@AuthenticationPrincipal UserDetails user, ModelMap model) {
        if (user == null) {
            return "redirect:/login"; // Prevent unauthorized access
        }
        model.addAttribute("job", new Job());
        return "post-job";
    }

    // Handle job posting
    @PostMapping("/post")
    public String postJob(@ModelAttribute Job job, @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return "redirect:/login";
        }
        jobRepo.save(job);
        return "redirect:/jobs/";
    }

    // Show job application form
    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, ModelMap model) {
        Optional<Job> jobOptional = jobRepo.findById(id);
        if (jobOptional.isEmpty()) {
            return "error"; // Redirect to error page if job not found
        }
        model.addAttribute("job", jobOptional.get());
        model.addAttribute("jobApplication", new JobApplication());
        return "apply-job";
    }

    // Handle job application submission
    @PostMapping("/apply/{id}")
    public String applyForJob(@PathVariable Long id, @ModelAttribute JobApplication jobApplication) {
        Optional<Job> jobOptional = jobRepo.findById(id);
        if (jobOptional.isEmpty()) {
            return "error"; // Redirect to error page if job not found
        }
        jobApplication.setJob(jobOptional.get());
        jobAppRepo.save(jobApplication);
        return "redirect:/jobs/";
    }
}
