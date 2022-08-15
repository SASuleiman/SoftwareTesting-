package org.suleiman.softwareTesting.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.suleiman.softwareTesting.JPA.StudentEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
    List<StudentEntity> findByDepartment(String department);
    List<StudentEntity> findByName(String name);
    List<StudentEntity> findByEmail(String email);
    StudentEntity findStudentById(Long id);
    StudentEntity findStudentByEmail(String email);
    Boolean findStudentsByEmail(String email);
}
