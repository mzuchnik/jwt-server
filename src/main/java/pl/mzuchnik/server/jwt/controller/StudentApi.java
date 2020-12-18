package pl.mzuchnik.server.jwt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.mzuchnik.server.jwt.entity.Student;
import pl.mzuchnik.server.jwt.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentApi {

    private final StudentService studentService;

    @Autowired
    public StudentApi(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> students(){
        return studentService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewStudent(@RequestBody Student student) { studentService.save(student); }


}
