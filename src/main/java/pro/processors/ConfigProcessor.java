package pro.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.dao.Student;

@Configuration
public class ConfigProcessor {
	Logger log = LoggerFactory.getLogger(ConfigProcessor.class);

	@Bean
	public ItemProcessor<Student, Student> processor() {
//		return student -> {
//			if (student.getMarks() < 65)
//				return student;
//			return null;
//		};
		log.trace("Processed entire Student Object No Conditions applied..");
		return student -> student;
	}

	@Bean
	public ItemProcessor<Student, Student> processor1() {
		log.trace("Transfering Data which are having marks less than 65");
		return student -> {
			if (student.getMarks() < 65)
				return student;
			return null;
		};
		// return student -> student;
	}

}
