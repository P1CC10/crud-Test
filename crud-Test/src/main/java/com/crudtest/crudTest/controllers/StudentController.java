package com.crudtest.crudTest.controllers;

import com.crudtest.crudTest.entities.Student;
import com.crudtest.crudTest.repositorys.StudentRepository;
import com.crudtest.crudTest.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student studentToAdd){
        return ResponseEntity.ok().body(studentService.addStudent(studentToAdd));
    }
    @GetMapping("/getall")
    public ResponseEntity<List<Student>>get(){
        return ResponseEntity.ok(studentService.getAll());
    }
    @GetMapping("/getsingle/{id}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable Long id){
        Optional<Student> studentOptional = studentService.getOne(id);
        if(studentOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(studentOptional.get());
    }
    @PutMapping("/putstudent/{id}")
    public ResponseEntity<Student> updateEvento(@PathVariable Long id, @RequestBody Student student) {
        Optional<Student> studentOptional = studentService.updateStudent(id, student);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(studentOptional.get());
    }
    @DeleteMapping("/deletesingle/{id}")
    public ResponseEntity<Student> deleteSingleStudent(@PathVariable Long id){
        Optional<Student> studentOptional = studentService.deleteById(id);
        if(studentOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(studentOptional.get());
    }
    @PatchMapping ("/active/{id}")
    public ResponseEntity<Student> studentActivation(@PathVariable Long id, @RequestParam Boolean isWorking) {
        Optional<Student> updatedStudent = studentService.studentActivation(id, isWorking);
        if (updatedStudent.isPresent()) {
            return ResponseEntity.ok(updatedStudent.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
