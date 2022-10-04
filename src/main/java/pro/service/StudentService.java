package pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.dao.Student;
import pro.repository.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	public Student getStudentById(Long id) throws Exception {
		return studentRepository.findById(id).orElseThrow(() -> new Exception("Student Id not found in database"));
	}
	
	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
	
	public Student getStudentByName(String name) throws Exception {
		return studentRepository.findByname(name).orElseThrow(() -> new Exception("Student name not found in database"));
	}
}
