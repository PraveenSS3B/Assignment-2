package pro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.dao.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByname(String name);
}
