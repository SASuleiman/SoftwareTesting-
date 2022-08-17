package org.suleiman.softwareTesting.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suleiman.softwareTesting.JPA.StudentEntity;
import org.suleiman.softwareTesting.Repositories.StudentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServices {
    Logger logger = LoggerFactory.getLogger(StudentServices.class.getName());

    @Autowired
    StudentRepository studentRepository;

    public StudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentEntity> listAllStudent() {
        return studentRepository.findAll();
    }

    public List<StudentEntity> findByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }


    public void addStudent(StudentEntity student) throws Exception {

        Boolean exists = studentRepository.findStudentsByEmail(student.getEmail());
        if(exists) {
            throw new IllegalArgumentException("this student already exists");
        }
        studentRepository.save(student);

    }

    public void deleteStudent(StudentEntity student) {
        // please take note that the exists method used here is quite useful instead of comparing using the equals sign

        Boolean exists = studentRepository.existsById(student.getId());
        if(!exists) {
            throw new IllegalArgumentException("The input with the email " + student.getEmail() + " does not exist");
        } else {
            studentRepository.deleteById(student.getId());
            logger.info("the student has successfully been deleted");
        }

    }

    public void deleteStudentById(Long studentId) {
        Boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalArgumentException("the student id is not valid");
        }  else {
            studentRepository.deleteById(studentId);
            logger.info("the student has been deleted successfully");
        }
    }

    @Transactional // the transactional annotation is used when one is writing to a database on more than one occasion, in the function below we might update both the name and email which means we are writing to the db more than once, all the other scenarios we are only writing to the db once
    public void updateStudentRecord(Long id, String email, String name) {
        Boolean exists = studentRepository.existsById(id);
        StudentEntity studentEntity = studentRepository.findStudentById(id);
        if(!exists) {
            throw new IllegalArgumentException("the student Id entered does not exists");
        } // optional can be used here

        if(name != null && name.length() > 0 && !name.equalsIgnoreCase(studentEntity.getName())) {
            studentEntity.setName(name);
            logger.info("the name has been successfully updated from " + studentEntity.getName() + " to " + name);
        }

        if(email != null && email.length() > 0 && !email.equalsIgnoreCase(studentEntity.getEmail())) {

            boolean empty = studentRepository.findByEmail(email).stream().filter((studEnt) -> studEnt.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList()).isEmpty();

            if(!empty) {
                throw new IllegalArgumentException("the email you selected already exists");
            }

            studentEntity.setEmail(email);
            logger.info("the email has been successfully updated from " + studentEntity.getEmail() + " to " + email);
               }

            studentRepository.save(studentEntity);

        }



    public String testing() {
        return "testing testing 123";
    }

}
