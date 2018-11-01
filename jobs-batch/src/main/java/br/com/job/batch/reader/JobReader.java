package br.com.job.batch.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.com.job.batch.model.Job;

@Component
public class JobReader {

	
	private final Integer DRAFT = 0;

	private static final Logger logger = LoggerFactory.getLogger(JobReader.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public List<Job> loadNewJobs() {
		List<Job> jobs = new ArrayList<Job>();
		try {						
			File file = new ClassPathResource("jobs.txt").getFile();
			FileInputStream instream = new FileInputStream(file);
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(instream));
			String line = buffer.readLine();
			while(line != null) {
				String[] fields = line.split("\\|");
				if(fields.length < 4 || !fields[0].matches("-?\\d+(.\\d+)?") ) {
					line = buffer.readLine();
					continue;
				}				
				Job job = new Job();
				job.setPartnerId(Integer.parseInt(fields[0]));
				job.setTitle(fields[1]);
				job.setCategoryId(Integer.parseInt(fields[2]));				
				job.setExpiresAt(new SimpleDateFormat("dd/MM/yyyy").parse(fields[3]));
				job.setStatus(DRAFT);
				jobs.add(job);
				line = buffer.readLine();
				
			}			
			PrintWriter pw = new PrintWriter(file.getAbsolutePath());
			pw.close();
			
	
		} catch (Exception e) {
			logger.error("Error to handle Jobs in File {} in {}", e.getMessage(), dateTimeFormatter.format(LocalDateTime.now()));
		}
		return jobs;
	}

}
