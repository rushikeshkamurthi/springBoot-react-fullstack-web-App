package com.ambcode.Student.dataAccessService;

import com.ambcode.Student.model_Entity.Student;
import com.ambcode.Student.model_Entity.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StudentDataAccessService {
    private  final JdbcTemplate jdbcTemplate;
    @Autowired
    public StudentDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> selectAllStudents(){
        String sql = "select student_id, first_name, last_name,email,gender from student";
        List<Student> students =   jdbcTemplate.query(sql, getStudentRowMapper());
      return students;
    }

    private RowMapper<Student> getStudentRowMapper() {
        return (rs, rowNum) -> {
            String studentIdStr = rs.getString("student_id");
            UUID studentId = UUID.fromString(studentIdStr);
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String genderStr = rs.getString("gender");
            Student.Gender gender = (genderStr == "MALE" || genderStr == "M" || genderStr == "male") ? Student.Gender.MALE : Student.Gender.FEMALE;
            return new Student(
                    studentId,
                    firstName,
                    lastName,
                    email,
                    gender
            );
        };
    }

    public int insertStudent(UUID studentId, Student student) {
        String sql = "INSERT INTO student (" +
                "student_id,"+
                "first_name,"+
                "last_name,"+
                "email,"+
                "gender )"+
                "VALUES(?,?,?,?,?::gender)"; // here :: gender is for casting custom data type in data base
        return jdbcTemplate.update(
                sql
                , studentId,
                student.getFirstname(),
                student.getLastname(),
                student.getEmail(),
                student.getGender().name().toUpperCase());

    }
    @SuppressWarnings("ConstantConditions")
    public boolean isEmailTaken(String email) {
        String sql = ""+"SELECT EXISTS (" +
                " SELECT 1 "+
                " FROM student "+
                " WHERE email = ? "+" ) ";
            return jdbcTemplate.queryForObject(sql,new Object[]{email}, (resultSet,i)->resultSet.getBoolean(1));

    }

    public List<StudentCourse> selectAllStudentCourses(UUID studentId) {
            String sql = ""+
                    " SELECT " +
                    " student.student_id, "+
                    " course.course_id, "+
                    " course.name, "+
                    " course.description, "+
                    " course.department, "+
                    " course.teacher_name, "+
                    " student_course.start_date, "+
                    " student_course.end_date, "+
                    " student_course.grade "+
                    " FROM student " +
                    " JOIN student_course USING(student_id) "+
                    " JOIN course       USING (course_id) "+
                    " WHERE student.student_id = ? ";
            return  jdbcTemplate.query(sql,new Object[]{studentId},mapStudentCourseFromDb());
    }
    private RowMapper<StudentCourse> mapStudentCourseFromDb(){
        return (resultSet,i)->new StudentCourse(
                UUID.fromString((resultSet.getString("student_id"))),
                UUID.fromString((resultSet.getString("course_id"))),
                resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("department"),
                        resultSet.getString("teacher_name"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate(),
                        Optional.ofNullable(resultSet.getString("grade")).map(Integer::parseInt).orElse(null)

        );
    }
}
