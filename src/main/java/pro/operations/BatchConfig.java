package pro.operations;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pro.dao.Student;
import pro.processors.ConfigProcessor;
import pro.readers.ConfigReader;
import pro.writers.ConfigWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ConfigReader configReader;

	@Autowired
	private ConfigWriter configWriter;

	@Autowired
	private ConfigProcessor configProcessor;
	
	@Autowired
	private DataSource dataSource;

	Logger log = LoggerFactory.getLogger(BatchConfig.class);

	@Bean
	public Step step1() throws MalformedURLException {
		log.trace("Performing Step 1");
		return stepBuilderFactory.get("Step 1").<Student, Student>chunk(2).reader(configReader.csvReader())
				.processor(configProcessor.processor()).writer(configWriter.writer()).build();
	}

	@Bean
	public Step step2() throws MalformedURLException {
		log.trace("Performing Step 2");
		return stepBuilderFactory.get("Step 2").<Student, Student>chunk(2).reader(configReader.dbReader())
				.processor(configProcessor.processor1()).writer(configWriter.fileItemWriter()).build();
	}

	@Bean
	public Job job() throws MalformedURLException {
		log.trace("Starting the Job....");
		return jobBuilderFactory.get("Job 2").incrementer(new RunIdIncrementer()).start(step1()).next(step2())
				.build();
	}

}
