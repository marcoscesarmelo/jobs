package br.com.job.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.job.exception.BusinessException;
import br.com.job.exception.NotFoundException;
import br.com.job.exception.UnauthorizedException;
import br.com.job.model.Job;
import br.com.job.model.User;
import br.com.job.repository.JobRepository;
import br.com.job.repository.UserRepository;

@RestController
@RequestMapping("/")
public class JobController {

	public final Integer DRAFT = 0, ACTIVE = 1;

	@Autowired
	JobRepository jobRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/jobs")
	public List<Job> list(@RequestHeader(value = "username") String usr,
			@RequestHeader(value = "password") String pwd) {
		this.validateUser(usr, pwd);
		List<Job> jobs = jobRepository.findAll();
		return jobs;
	}

	@PostMapping("/jobs")
	public Job create(@Valid @RequestBody Job job, @RequestHeader(value = "username") String usr,
			@RequestHeader(value = "password") String pwd) {
		this.validateUser(usr, pwd);
		if (job.getExpiresAt().before(new Date())) {
			throw new BusinessException("/jobs", "mensagem",
					"Data de Expiracao devera ser igual ou maior que a data recente");
		}

		job.setStatus(DRAFT);
		Job newJob = jobRepository.save(job);
		return newJob;
	}

	@PostMapping("/jobs/{partnerId}/activate")    
	public Job percentage(@PathVariable(value = "partnerId") Integer id, @RequestHeader(value = "username") String usr,
			@RequestHeader(value = "password") String pwd) {
		
		this.validateUser(usr, pwd);
		Optional<Job> job = jobRepository.findById(id);
    	if(job.get() == null) {
    		throw new NotFoundException("/job", "id", id );
    	}					
		jobRepository.updateJobStatus(ACTIVE, id);
		Job activeJob = job.get();
		activeJob.setStatus(ACTIVE);
		return activeJob;
	}
	
	@GetMapping("/category/{id}")
	public String percentage(@PathVariable(value = "id") Integer id) {
		float total = 0.0f, actives = 0.0f, percentage = 0.0f;
		
		List<Job> jobs = jobRepository.findByCategoryId(id);
		total = jobs.size();
		for(Job job : jobs) {
			if(job.getStatus() == ACTIVE) {
				actives++;
			}
		}
		if(total > 0) {
			percentage = actives/total;
		}
		return String.format("%.2f", percentage);

	}	
	
	public void validateUser(String username, String password) {
		
		Optional<User> user = userRepository.findByUsername(username);		
		if (user.get() == null || !user.get().getUsername().equals(username)
				|| !user.get().getPassword().equals(password)) {
			throw new UnauthorizedException("/jobs", "username", user.get().getUsername());
		}
	}

}
