package pl.mzuchnik.server.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.mzuchnik.server.jwt.entity.Student;
import pl.mzuchnik.server.jwt.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepo studentRepo;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    @Override
    public Optional<Student> findById(long id) {
        return studentRepo.findById(id);
    }

    @Override
    public void save(Student student) {
        studentRepo.save(student);
    }

    @Override
    public void delete(Student student) {
        studentRepo.delete(student);
    }

    @Override
    public void deleteById(long id) {
        studentRepo.deleteById(id);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseWithStudents(){
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jan","Kowalski",52));
        students.add(new Student("Barbara","Nowacka",43));
        students.add(new Student("Katarzyna","Nowak",24));
        students.add(new Student("Piotr","Babacki",33));
        students.add(new Student("Joanna","Tabacka",41));
        students.add(new Student("Mateusz","Nowoczes",21));
        students.add(new Student("Arnold","Sowacki",21));

        studentRepo.saveAll(students);
    }
}
