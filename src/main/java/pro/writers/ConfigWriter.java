package pro.writers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import pro.dao.Student;
import pro.repository.StudentRepository;

@Configuration
public class ConfigWriter {
	Logger log = LoggerFactory.getLogger(ConfigWriter.class);
	@Autowired
	private StudentRepository studentRepository;

	@Bean
	public ItemWriter<Student> writer() {
		log.trace("Writing into Database...");
		return students -> studentRepository.saveAll(students);
	}
	

	@Bean
	public FlatFileItemWriter<Student> fileItemWriter() {
		FlatFileItemWriter<Student> writer = new FlatFileItemWriter<Student>();
		writer.setResource(new FileSystemResource(
				System.getProperty("user.dir") + "/src/main/resources/output/studentFromDB.csv"));
		DelimitedLineAggregator<Student> delimitedLineAggregator = new DelimitedLineAggregator<Student>();
		delimitedLineAggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<Student> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<Student>();
		beanWrapperFieldExtractor.setNames(new String[] { "id", "classRoom", "gender", "marks", "name" });
		delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

		writer.setLineAggregator(delimitedLineAggregator);
		log.trace("Writing into studentFromDB.csv from Database....");
		return writer;
	}

}
