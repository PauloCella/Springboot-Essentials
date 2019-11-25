package br.com.devdojo;


import br.com.devdojo.model.Student;
import br.com.devdojo.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class StudentEndpointTokenTest {

    @Autowired
    public TestRestTemplate restTemplate;

    @LocalServerPort
    public int port;

    @MockBean
    public StudentRepository studentRepository;

    @Autowired
    public MockMvc mockMvc;

    public HttpEntity<Void> protectedHeader;
    public HttpEntity<Void> adminHeader;
    public HttpEntity<Void> wrongHeader;

    @Before
    public void configProtectedHeaders(){
        String str = "{\"username\":\"joaozito\", \"password\":\"pcella\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
        this.protectedHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configAdminHeaders(){
        String str = "{\"username\":\"paulo\", \"password\":\"pcella\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
        this.adminHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configWrongHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "111111");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @Before
    public void setup(){
        Student student = new Student(1L, "Legolas", "lego@lotr.com");
        BDDMockito.when(studentRepository.findById(student.getId()).get()).thenReturn(student);
    }

    @Test
    public void listStudentsWhenTokenIsIncorrectShouldReturnStatusCode403(){
        ResponseEntity<String> response = restTemplate.exchange("/v1/protected/students/", HttpMethod.GET, wrongHeader, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void getStudentsByIdWhenTokenIsIncorrectShouldReturnStatusCode403(){
        ResponseEntity<String> response = restTemplate.exchange("/v1/protected/students/176980", HttpMethod.GET, wrongHeader, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }


}
