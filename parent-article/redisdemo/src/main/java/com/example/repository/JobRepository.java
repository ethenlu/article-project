package com.example.repository;

import com.example.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {


    @Query(value = "SELECT user.`name` , job.`name` , job.`price` FROM job,USER,user_job WHERE user_job.`uid` = user.`id` AND job.`id`= user_job.`jid` AND `user`.`id`= ?1",nativeQuery = true)
    public List<Job> findAllBy(Integer id);

}
