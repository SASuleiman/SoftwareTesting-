package org.suleiman.softwareTesting.JPA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.suleiman.softwareTesting.Repositories.StudentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

@Component
public class StudentCommandLineRunner implements CommandLineRunner {

    @Autowired
    StudentRepository repository;
private static final Logger logger = LoggerFactory.getLogger(StudentCommandLineRunner.class.getName());
    @Override
    public void run(String... args) throws Exception {
        logger.info("------------Adding Student Using Command Line Runner--------------");
        repository.save(new StudentEntity("Mathew","Mathew@kwasu.com","ECE", LocalDate.of(1999,Month.AUGUST,12)));
        repository.save(new StudentEntity("Seyi","Seyi@Kwasu.com","MLS",LocalDate.of(2000,Month.JUNE,23)));
        repository.save(new StudentEntity("Tope","Tope@gmail.com","MEE",LocalDate.of(2004,Month.FEBRUARY,12)));
        logger.info("--------------------------------------------------------------------------");

//        for(StudentEntity student: repository.findAll()) {
//            logger.info(student.toString());
//        }

        repository.findAll().stream().forEach(System.out::println);


    }
}
