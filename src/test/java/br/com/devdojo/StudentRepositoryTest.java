package br.com.devdojo;

import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData(){
        Student student = new Student("Paulo", "paulo@cella.com");
        this.studentRepository.save(student);

        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Paulo");
        assertThat(student.getEmail()).isEqualTo("paulo@cella.com");

    }

    @Test
    public void deleteSouldRemoveData(){
        Student student = new Student("Paulo", "paulo@cella.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);

        assertThat(studentRepository.findById(student.getId())).isEmpty();

    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Student student = new Student("Paulo", "paulo@cella.com");
        this.studentRepository.save(student);
        student.setName("Willian");
        student.setEmail("willian@coco.com");
        this.studentRepository.save(student);
        student = this.studentRepository.findById(student.getId()).get();


        assertThat(student.getName()).isEqualTo("Willian");
        assertThat(student.getEmail()).isEqualTo("willian@coco.com");

    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase(){
        Student student = new Student("Paulo", "paulo@cella.com");
        Student student2 = new Student("paulo", "paulo222@cella.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);

        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("paulo");

        assertThat(studentList.size()).isEqualTo(2);


    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo nome do estudante é obrigatório");

        this.studentRepository.save(new Student());
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException(){
        thrown.expect(ConstraintViolationException.class);

        Student student = new Student();
        student.setName("paulo");

        this.studentRepository.save(student);
    }

    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Digite um email válido");


        Student student = new Student();
        student.setName("paulo");
        student.setEmail("paulo");

        this.studentRepository.save(student);
    }

}
