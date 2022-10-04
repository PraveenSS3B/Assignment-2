package pro.readers;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import pro.dao.Student;
import pro.operations.StudentRowMapper;

@Configuration
public class ConfigReader {

	@Autowired
	private DataSource dataSource;

	Logger log = LoggerFactory.getLogger(ConfigReader.class);

	@Bean
	public FlatFileItemReader<Student> csvReader() throws MalformedURLException {
		FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new ClassPathResource("input/student.csv"));
		flatFileItemReader.setLineMapper(getLineMapper());
		flatFileItemReader.setLinesToSkip(1);
		log.trace("Read From student.csv file");
		return flatFileItemReader;
	}

	public LineMapper<Student> getLineMapper() {
		DefaultLineMapper<Student> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id", "name", "classRoom", "marks", "gender" });
		// lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3 });

		BeanWrapperFieldSetMapper<Student> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<Student>();
		beanWrapperFieldSetMapper.setTargetType(Student.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		return defaultLineMapper;
	}


	@Bean
	public JdbcCursorItemReader<Student> dbReader() {
		JdbcCursorItemReader<Student> jdbcCursorItemReader = new JdbcCursorItemReader<Student>();
		jdbcCursorItemReader.setDataSource(dataSource);
		jdbcCursorItemReader.setSql("SELECT ID, CLASS_ROOM, GENDER, MARKS, NAME FROM STUDENT");
		jdbcCursorItemReader.setRowMapper(new StudentRowMapper());
		log.trace("Reading From Database....");
		return jdbcCursorItemReader;
	}




}
