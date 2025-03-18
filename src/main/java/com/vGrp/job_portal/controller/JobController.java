package com.vGrp.job_portal.controller;

import ch.qos.logback.core.model.Model;
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

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepo;  // Ensures jobRepo is initialized

    @Autowired
    private JobApplicationRepository jobAppRepo;

    @GetMapping("/post")
    public String showPostJobForm(ModelMap model) {
        model.addAttribute("job", new Job());
        return "post-job";
    }

    @PostMapping("/post")
    public String postJob(@ModelAttribute Job job) {
        jobRepo.save(job);
        return "redirect:/jobs";
    }
    @GetMapping("/post")
    public String showPostJobForm(@AuthenticationPrincipal UserDetails user, ModelMap model) {
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("job", new Job());
        return "post-job";
    }
    @GetMapping("/")
    public String listJobs(ModelMap model) {
        List<Job> jobs = jobRepo.findAll();  // Fetch all jobs
        model.addAttribute("jobs", jobs);   // Add to Thymeleaf model
        return "jobs";  // Renders jobs.html
    }
    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, ModelMap model) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Job ID"));
        model.addAttribute("job", job);
        model.addAttribute("jobApplication", new JobApplication());
        return "apply-job";
    }

    // Handle Job Application Submission
    @PostMapping("/apply/{id}")
    public String applyForJob(@PathVariable Long id, @ModelAttribute JobApplication jobApplication) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Job ID"));
        jobApplication.setJob(job);
        jobAppRepo.save(jobApplication);
        return "redirect:/jobs/";
    }

    // Show Job Form
    @GetMapping("/add")
    public String showAddJobForm(ModelMap model) {
        model.addAttribute("job", new Job()); // Empty job object for form
        return "add-job"; // Thymeleaf template
    }

    // Handle Job Posting
    @PostMapping("/add")
    public String addJob(@ModelAttribute Job job) {
        jobRepo.save(job); // Save job to database
        return "redirect:/jobs/"; // Redirect to job listing page
    }

    @GetMapping("/post")
    public String showJobPostForm(ModelMap model) {
        model.addAttribute("job", new Job());  // Empty job object for form
        return "post-job";  // Renders post-job.html
    }


}