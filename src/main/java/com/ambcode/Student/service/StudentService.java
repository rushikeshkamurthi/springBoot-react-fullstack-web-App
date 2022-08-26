package com.ambcode.Student.service;

import com.ambcode.Student.EmailValidator;
import com.ambcode.Student.dataAccessService.StudentDataAccessService;
import com.ambcode.Student.exeception.ApiRequestException;
import com.ambcode.Student.model_Entity.Student;
import com.ambcode.Student.model_Entity.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private final StudentDataAccessService studentDataAccessService;
    private final EmailValidator emailValidator;

    public StudentService(StudentDataAccessService studentDataAccessService, EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }

    public void addNewStudent( Student student) {
        addNewStudent(null,student);
    }

    public void addNewStudent(UUID studentId, Student student) {
        UUID newStudentId = Optional.ofNullable(studentId).orElse(UUID.randomUUID());

        // TODO verify valid email
       if ( ! emailValidator.test((student.getEmail()))){
            throw new ApiRequestException(student.getEmail()+" is Not valid email");
        }
 if(studentDataAccessService.isEmailTaken(student.getEmail())){
     throw new ApiRequestException(student.getEmail()+" is Already Exist");
 }
        this.studentDataAccessService.insertStudent(newStudentId,student);
    }
    //TODO verify That email is not taken
    public List<Student> getAllStudents(){
        return studentDataAccessService.selectAllStudents();
    }

    public List<StudentCourse> getAllCoursesForStudent(UUID studentId) {
        return studentDataAccessService.selectAllStudentCourses(studentId);
    }
}
