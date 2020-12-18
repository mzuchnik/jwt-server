package pl.mzuchnik.server.jwt.service;

import pl.mzuchnik.server.jwt.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    Optional<Student> findById(long id);

    void save(Student student);

    void delete(Student student);

    void deleteById(long id);
}
