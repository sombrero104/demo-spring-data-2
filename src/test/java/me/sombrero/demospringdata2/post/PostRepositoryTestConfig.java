package me.sombrero.demospringdata2.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 이벤트 리스너 테스트(PostRepositoryTest)를 위해
 * PostPublishedEvent 이벤트(도메인 클래스(Post.class와 같은)의 변화)를 감지하는
 * 커스텀 리스너인 PostListener를 빈으로 등록해준다.
 * PostRepositoryTest에서는 '@Import(PostRepositoryTestConfig.class)'해서 사용한다.
 */
@Configuration
public class PostRepositoryTestConfig {

    /*@Bean
    public PostListener postListener() {
        return new PostListener();
    }*/

    @Bean
    public ApplicationListener<PostPublishedEvent> postListener() {
        return new ApplicationListener<PostPublishedEvent>() {
            @Override
            public void onApplicationEvent(PostPublishedEvent event) {
                System.out.println("-----------------------------------");
                System.out.println("##### [2] " + event.getPost().getTitle() + " is published!!");
                System.out.println("-----------------------------------");
            }
        };
    }

}
