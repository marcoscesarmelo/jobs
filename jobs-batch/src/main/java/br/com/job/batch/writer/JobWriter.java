package br.com.job.batch.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.job.batch.model.Job;
import br.com.job.batch.repository.JobRepository;

@Component
public class JobWriter {

	@Autowired
	JobRepository jobRepository;

	public String loadJob(Job job) {
		try {
			if (!jobRepository.findById(job.getPartnerId()).isPresent()) {
				jobRepository.save(job);
			}
			return "OK, Loaded successfuly!";
		} catch (Exception e) {
			return "Something wrong occurred! " + e.getMessage();
		}
	}

}
