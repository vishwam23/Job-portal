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

    // ✅ List all jobs (Fixed "/jobs/" issue)
    @GetMapping
    public String listJobs(ModelMap model) {
        List<Job> jobs = jobRepo.findAll();
        model.addAttribute("jobs", jobs);
        return "jobs"; // Make sure "jobs.html" exists in /templates
    }

    // ✅ Show job posting form (Only accessible if logged in)
    @GetMapping("/post")
    public String showPostJobForm(@AuthenticationPrincipal UserDetails user, ModelMap model) {
        if (user == null) {
            return "redirect:/login"; // Prevent unauthorized access
        }
        model.addAttribute("job", new Job());
        return "post-job"; // Ensure "post-job.html" exists
    }

    // ✅ Handle job posting (Fixed redirect)
    @PostMapping("/post")
    public String postJob(@ModelAttribute Job job, @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return "redirect:/login";
        }
        jobRepo.save(job);
        return "redirect:/jobs"; // ✅ Corrected redirect
    }

    // ✅ Show job application form
    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, ModelMap model) {
        Optional<Job> jobOptional = jobRepo.findById(id);
        if (jobOptional.isEmpty()) {
            return "redirect:/"; // ✅ Redirecting to home if job not found
        }
        model.addAttribute("job", jobOptional.get());
        model.addAttribute("jobApplication", new JobApplication());
        return "apply-job"; // Ensure "apply-job.html" exists
    }

    // ✅ Handle job application submission (Fixed errors)
    @PostMapping("/apply/{id}")
    public String applyForJob(
            @PathVariable Long id,
            @RequestParam String applicantName,
            @RequestParam String email,
            @RequestParam String resume) {

        Optional<Job> jobOptional = jobRepo.findById(id);
        if (jobOptional.isEmpty()) {
            return "redirect:/"; // ✅ Redirect to home if job not found
        }

        Job job = jobOptional.get();
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJob(job);
        jobApplication.setApplicantName(applicantName);
        jobApplication.setEmail(email);
        jobApplication.setResume(resume);

        jobAppRepo.save(jobApplication);
        return "redirect:/jobs"; // ✅ Corrected redirect
    }
}
