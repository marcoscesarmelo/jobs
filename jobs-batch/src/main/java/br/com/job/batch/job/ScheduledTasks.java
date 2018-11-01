package br.com.job.batch.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.job.batch.model.Job;
import br.com.job.batch.reader.JobReader;
import br.com.job.batch.writer.JobWriter;

@Component
public class ScheduledTasks {

	@Autowired
	JobWriter jobWriter;

	@Autowired
	JobReader jobReader;

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Scheduled(fixedDelay = 2000)
	public void scheduleTaskWithFixedDelay() {
		logger.info("Getting New Job Opportunities :: Execution Time - {}",
				dateTimeFormatter.format(LocalDateTime.now()));
		try {
			TimeUnit.SECONDS.sleep(5);
			List<Job> newJobs = jobReader.loadNewJobs();

			if (newJobs.isEmpty()) {
				logger.info("Nothing to process at {}", dateTimeFormatter.format(LocalDateTime.now()));
			} else {
				for (Job job : newJobs) {
					jobWriter.loadJob(job);
				}
			}

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
