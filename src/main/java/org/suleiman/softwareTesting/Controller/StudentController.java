package org.suleiman.softwareTesting.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.suleiman.softwareTesting.JPA.StudentEntity;
import org.suleiman.softwareTesting.Services.StudentServices;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class StudentController {
   public static final Logger logger = LoggerFactory.getLogger(StudentController.class.getName());
    @Autowired
    StudentServices services;


    @GetMapping(value = "/getStudents")
    public List<StudentEntity> getStudents() {
        return services.listAllStudent();
    }

    @GetMapping(value = "/getStudentByDepartment/{department}")
    public List<StudentEntity> findByDepartment(@PathVariable String department) {
        return services.findByDepartment(department);
    }

//    @PostMapping(value = "/addStudent/{name}/{department}/{email}")
//    public void addStudent(@PathVariable String name,@PathVariable String department,@PathVariable String email) {
//
//        try {
//        services.addStudent(name,email,department);
//        } catch (Exception exception ) {
//            logger.info("Student already exist");
//        }
//
//    }
    @PostMapping(value = "/addStudent")
    public void addStudent1(@RequestBody StudentEntity student) {
        try {
        services.addStudent(student);

        } catch (Exception exception ) {
            logger.error("the user already exists try again");
        }
    }

    @DeleteMapping(value = "/deleteStudent")
    public void deleteStudent(@RequestBody StudentEntity student) {
       services.deleteStudent(student);
    }

    @DeleteMapping(value = "/deleteStudent/{studentId}")
    public void deleteStudentById(@PathVariable Long studentId) {
        services.deleteStudentById(studentId);
    }

    @PutMapping(value = "/updateStudent/{studentId}")
    public void updateStudent(@PathVariable Long studentId, @RequestParam(value = "name",required = false)String name , @RequestParam(value = "email", required = false) String email ) {

        services.updateStudentRecord(studentId,email,name);
    }
}
