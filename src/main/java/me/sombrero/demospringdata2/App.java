package me.sombrero.demospringdata2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// @EnableJpaRepositories(repositoryImplementationPostfix = "Default") // 구현체명에 Imple말고 다른 것을 붙이고 싶은 경우.
@EnableJpaRepositories(repositoryBaseClass = MyRepositoryImpl.class)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
