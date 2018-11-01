package br.com.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.job.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
	
	@Modifying
	@Transactional
	@Query("update Job j set j.status = ?1 where j.partnerId = ?2")
	int updateJobStatus(Integer status, Integer partnerId);
	
	List<Job> findByCategoryId(Integer categoryId);


}