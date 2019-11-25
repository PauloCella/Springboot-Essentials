package br.com.devdojo.javaClient;

import br.com.devdojo.model.Student;

import java.util.List;


public class JavaSpringClientTeste {
    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("John Wick2");
        studentPost.setEmail("john@pencil.com");

//        studentPost.setId(5L);

        JavaClientDAO dao = new JavaClientDAO();
//        System.out.println(dao.findById(6L));


//        dao.update(studentPost);

        dao.delete(5L);



    }
}
