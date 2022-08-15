package org.suleiman.softwareTesting.Repositories;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.suleiman.softwareTesting.JPA.StudentEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


//@SpringBootTest
@DataJpaTest
class StudentRepositoryTest {
    Logger logger = LoggerFactory.getLogger(StudentRepositoryTest.class.getName());

    @BeforeEach
    void displayExecutionStatus() {

    }

    @AfterEach
    void tearDown() {
        logger.info("Test has been completed deleting content from the repo ");
        studentRepository.deleteAll();
    }
    @Autowired
    StudentRepository studentRepository;

    @Test
    void itShouldTestFindByDepartment() {
        //given
        String department = "MLS";
        LocalDate dob = LocalDate.of(2000,Month.FEBRUARY,12);
        StudentEntity student = new StudentEntity("Jamila","Jamila@gmail.com",department,LocalDate.of(2000,Month.AUGUST,12));
        studentRepository.save(student);


        // when
        List<StudentEntity> expectedResults =  studentRepository.findByDepartment("MLS");
        logger.info(expectedResults.toString());

        // then perform the assertions that you would like to happen
        for(StudentEntity student1 : expectedResults) {
        assertThat(student1.getDepartment()).isEqualTo(student.getDepartment());
        logger.warn(Integer.toString(student1.getAge()));
        }
    }

    @Test
    void itShouldTestFindByName() {
        // given
        String name = "Jamila";
        StudentEntity student = new StudentEntity(name,"jamila@gmail.com","MLS",LocalDate.of(2000, Month.AUGUST,12));
        studentRepository.save(student);
        // when
        List<StudentEntity> expectedResult = studentRepository.findByName("Jamila");
        logger.info(expectedResult.toString()) ;

        // then perform the assertions
        for(StudentEntity studentEntity : expectedResult) {
            assertThat(studentEntity.getName()).isEqualTo("Jamila");
        }
    }

    @Test
    void itShouldTestFindByEmail() {

    }

    @Test
    void itShouldTestFindStudentById() {
        // given
        StudentEntity student = new StudentEntity("Jamal", "Jamal@interswitchng.com", "ILS", LocalDate.of(2000,Month.AUGUST,12));
        studentRepository.save(student);


        // when
        StudentEntity expectedResult = studentRepository.findStudentById(student.getId());
        logger.info(expectedResult.toString());

        // perform the assertions
        assertThat(expectedResult.getId()).isEqualTo(student.getId());
    }

    private StudentEntity getStudent() {
        StudentEntity student = new StudentEntity();
        student.setEmail("Jamila@gmail.com");
        student.setName("Jamila");
        student.setDob(LocalDate.of(2000, Month.AUGUST,23));
        student.setDepartment("MLS");
        return  student;
    }
}