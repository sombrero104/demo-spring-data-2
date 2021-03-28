package me.sombrero.demospringdata2.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 이벤트 리스너 테스트를 위해
 * PostPublishedEvent 이벤트(도메인 클래스(Post.class와 같은)의 변화)를 감지하는
 * 커스텀 리스너인 PostListener를 빈으로 등록해준다.
 */
@Configuration
public class PostRepositoryTestConfig {

    @Bean
    public PostListener postListener() {
        return new PostListener();
    }

}
