package com.vGrp.job_portal.repository;

import com.vGrp.job_portal.model.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContaining(String title);  // Search jobs by title

    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.closed = true WHERE j.id = :jobId")
    void closeJob(@Param("jobId") Long jobId);
}