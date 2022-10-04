package pro.serviceTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import pro.dao.Student;
import pro.repository.StudentRepository;
import pro.service.StudentService;

@SpringBootTest
public class StudentServiceTest {
	
	@MockBean
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;
	
	@Test
	void testGetStudentById() throws Throwable{
		Student student = new Student();
		student.setId(1L);
		student.setName("max kol");
		student.setClassRoom("eight");
		student.setMarks(99L);
		student.setGender("Male");
		Optional<Student> s1 = Optional.of(student);
		Mockito.when(studentRepository.findById(student.getId())).thenReturn(s1);
		assertThat(studentService.getStudentById(1L)).isEqualTo(student);
		
	}
	
	@Test
	void testGetAllStudents() throws Throwable{
		ArrayList<Student> al = new ArrayList<Student>();
		Student student = new Student();
		student.setId(1L);
		student.setName("max kol");
		student.setClassRoom("eight");
		student.setMarks(99L);
		student.setGender("Male");
		
		Student student1 = new Student();
		student1.setId(2L);
		student1.setName("jack henry");
		student1.setClassRoom("eleven");
		student1.setMarks(87L);
		student1.setGender("Male");
		
		Student student2 = new Student();
		student2.setId(3L);
		student2.setName("ken johnson");
		student2.setClassRoom("five");
		student2.setMarks(57L);
		student2.setGender("Male");
		
		al.add(student);
		al.add(student1);
		al.add(student2);
		
		Mockito.when(studentRepository.findAll()).thenReturn(al);
		assertThat(studentService.getAllStudents()).isEqualTo(al);
	}
	
	@Test
	void testGetStudentByName() throws Throwable{
		Student student = new Student();
		student.setId(1L);
		student.setName("max kol");
		student.setClassRoom("eight");
		student.setMarks(99L);
		student.setGender("Male");
		Optional<Student> s1 = Optional.of(student);
		Mockito.when(studentRepository.findByname(student.getName())).thenReturn(s1);
		assertThat(studentService.getStudentByName(student.getName())).isEqualTo(student);
		
	}

}
