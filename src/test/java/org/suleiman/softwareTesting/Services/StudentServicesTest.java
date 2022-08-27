package org.suleiman.softwareTesting.Services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.suleiman.softwareTesting.JPA.StudentEntity;
import org.suleiman.softwareTesting.Repositories.StudentRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServicesTest {
Logger logger = LoggerFactory.getLogger(StudentServicesTest.class.getName());
  private StudentServices studentServices;
//  private AutoCloseable autoCloseable;
  @Mock
  private StudentRepository studentRepository;

  @BeforeEach
  void setup() {
//    autoCloseable = MockitoAnnotations.openMocks(this);
    studentServices = new StudentServices(studentRepository);
  }

  @AfterEach
  void tearDown() throws Exception {
//    autoCloseable.close();
    logger.info("test has been completed using the mockito");

  }

  @Test
    void testTOListAllStudent() {
    //given

    //when
    studentServices.listAllStudent();
    //assertion
    verify(studentRepository).findAll();
    }

    @Test
    void testForFindByDepartment() {
    //given
      StudentEntity student = getStudent();
    // when
     studentServices.findByDepartment(student.getDepartment());
    // assert(then)
      verify(studentRepository).findByDepartment(student.getDepartment());
    }

    @Test
    void testForAddStudent() throws Exception {
    //given
      StudentEntity student = getStudent();

    // when
      studentServices.addStudent(student);

    // assertions

      // the initialization of the argument captor for the student entity class
      ArgumentCaptor<StudentEntity> studentArgumentCaptor = ArgumentCaptor.forClass(StudentEntity.class);

      // the argument captor is used to capture the value that was saved in the student repo
      verify(studentRepository).save(studentArgumentCaptor.capture());

      StudentEntity captured = studentArgumentCaptor.getValue();
      assertThat(captured).isEqualTo(student);

    }

    @Test
    void testForThrowingExceptionIfCannotAdd() throws Exception {
    // given
      StudentEntity student = getStudent();

    //when
    given(studentRepository.findStudentsByEmail(student.getEmail())).willReturn(true);

    // assertion(then)
      assertThatThrownBy(()-> studentServices.addStudent(student)).hasMessageContaining("this student already exists");
    }

    @Test
    void testSuccessfulDeleteOfStudent() {
    // given
   StudentEntity student = getStudent();
   studentRepository.save(student);
   logger.info(Long.toString(student.getId()));

    //when
        given(studentRepository.existsById(student.getId())).willReturn(true);
    studentServices.deleteStudent(student);

    //then
      verify(studentRepository).deleteById(student.getId());
    }

    @Test
    void testExceptionThrownForDeleteByStudent() {
      //given
        StudentEntity student = getStudent();
        studentRepository.save(student);

    // when
        given(studentRepository.existsById(student.getId())).willReturn(false);

    // then
        assertThatThrownBy(() -> studentServices.deleteStudent(student)).hasMessageContaining("The input with the email " + student.getEmail() + " does not exist");
    }
    @Test
    void deleteStudentById() {
    }

    @Test
    void testToUpdateStudentRecord() {
      // given
     StudentEntity student = getStudent();
     studentRepository.save(student);


     // when
        given(studentRepository.existsById(student.getId())).willReturn(true);
        Mockito.when(studentRepository.findStudentById(student.getId())).thenReturn(student);
      studentServices.updateStudentRecord(student.getId(),"jamila1@gmail.com","jamila udhayfha");

    // then
    assertThat(student.getEmail()).isEqualTo("jamila1@gmail.com");
    assertThat(student.getName()).isEqualTo("jamila udhayfha");
    }

//    @Test
//    void testToCheckIfEmailAlreadyExists() {
//        // given
//        StudentEntity student = getStudent();
//
//
//        // when
//        given(studentRepository.existsById(student.getId())).willReturn(true);
//        Mockito.when(studentRepository.findStudentById(student.getId())).thenReturn(student);
//        studentServices.updateStudentRecord(student.getId(),"jamila@gmail.com","jamila udhayfha");
//
//
//        // then
//        assertThatThrownBy(() -> studentServices.updateStudentRecord(student.getId(),student.getEmail(),"jamila udhayfha")).hasMessageContaining("the email you selected already exists");
//    }

    @Test
    void successfulDeleteStudentById() {
      // given
        StudentEntity student = getStudent();
        studentRepository.save(student);
        // when
        given(studentRepository.existsById(student.getId())).willReturn(true);
        studentServices.deleteStudentById(student.getId());

        // then
        verify(studentRepository).deleteById(student.getId());

    }

    @Test
    void exceptionFromGetStudentById() {
      // given
        StudentEntity student = getStudent();
        studentRepository.save(student);

        // when
        given(studentRepository.existsById(student.getId())).willReturn(false);

        // then
        assertThatThrownBy(() -> studentServices.deleteStudentById(student.getId())).hasMessageContaining("the student id is not valid");
    }
    @Test
    void testForExceptionInUpdateStudentRecord() {
      //given
        StudentEntity student  = getStudent();
        studentRepository.save(student);

     //when
        given(studentRepository.existsById(student.getId())).willReturn(false);

    //then
        assertThatThrownBy(()-> studentServices.updateStudentRecord(student.getId(),student.getEmail(),student.getName())).hasMessageContaining("the student Id entered does not exists");
    }


  private StudentEntity getStudent() {
    StudentEntity student = new StudentEntity();
    student.setId(1L);
    student.setEmail("Jamila@gmail.com");
    student.setName("Jamila");
    student.setDob(LocalDate.of(2000, Month.AUGUST,23));
    student.setDepartment("MLS");
    return  student;
  }
}