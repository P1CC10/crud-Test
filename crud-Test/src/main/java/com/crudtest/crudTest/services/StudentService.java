package com.crudtest.crudTest.services;

import com.crudtest.crudTest.entities.Student;
import com.crudtest.crudTest.repositorys.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student){
        return studentRepository.saveAndFlush(student);
    }
    public List<Student> getAll(){
        return studentRepository.findAll();
    }
    public Optional<Student> getOne(Long id){
        return studentRepository.findById(id);
    }
    public Optional<Student>updateStudent(Long id,Student student1){
        Optional<Student> studenteOptional = studentRepository.findById(id);
        if(studenteOptional.isPresent()){
            studenteOptional.get().setName(student1.getName());
            studenteOptional.get().setLastName(student1.getLastName());
            Student studentUpdated = studentRepository.save(studenteOptional.get());
            return Optional.of(studentUpdated);
        }
        return Optional.empty();
    }
    public Optional<Student>deleteById(Long id){
        Optional<Student> studentOptional= studentRepository.findById(id);
        if(studentOptional.isPresent()){
            studentRepository.delete(studentOptional.get());
        }else{
            return Optional.empty();
        }
        return studentOptional;
    }
    public Optional<Student> studentActivation(Long id, Boolean isWorking){
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()){
            studentOptional.get().setWorking(isWorking);
            Student studentUpdated = studentRepository.save(studentOptional.get());
            return Optional.of(studentUpdated);
        }else{
            return Optional.empty();
        }

    }

}
