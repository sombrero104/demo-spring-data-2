package me.sombrero.demospringdata2.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PostRepositoryTestConfig.class)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test void event() {
        Post post = new Post();
        post.setTitle("Event :)");
        PostPublishedEvent event = new PostPublishedEvent(post); // 이벤트를 만든다.
        applicationContext.publishEvent(event); // ApplicationContext로 이벤트를 던진다.
        /**
         * PostRepositoryTestConfig에서 빈으로 등록된 커스텀 리스너인 PostListener를 import해서
         * 위에서 ApplicationContext로 던진(publish한) 이벤트를
         * PostListener가 잡아서 PostListener에 있는 onApplicationEvent() 메소드를 실행한다.
         */
    }

    @Test
    public void crud() {
        // postRepository.findMyPost();
        Post post = new Post();
        post.setTitle("Hibernate :D");

        assertThat(postRepository.contains(post)).isFalse(); // Transient 상태.

        postRepository.save(post);

        assertThat(postRepository.contains(post)).isTrue(); // Persistent 상태.

        // postRepository.findMyPost();
        postRepository.delete(post); // 테스트 코드이기 때문에 어차피 롤백이라 무시된다.
        postRepository.flush();
    }

}