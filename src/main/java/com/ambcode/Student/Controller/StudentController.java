package com.ambcode.Student.Controller;

import com.ambcode.Student.model_Entity.Student;
import com.ambcode.Student.model_Entity.StudentCourse;
import com.ambcode.Student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/student")
public class StudentController {
    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public  List<Student> getAllStudent(){
       // throw new ApiRequestException("ops ! Something went wrong from our side No data available");
        return studentService.getAllStudents();
    }
    @PostMapping
    public void addNewStudent(@RequestBody @Valid Student student){ //RequestBody annotaion is use to tell student object will consume body part of request obeject in the header from client request
        studentService.addNewStudent(student);
    }

    @GetMapping(path="{studentId}/courses")
    public List<StudentCourse> getAllCoursesForStudent(@PathVariable("studentId") UUID studentId){

        return studentService.getAllCoursesForStudent(studentId);
    }
}
