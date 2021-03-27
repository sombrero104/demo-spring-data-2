package me.sombrero.demospringdata2.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void crud() {
        postRepository.findMyPost();
        Post post = new Post();
        post.setTitle("Hibernate :D");
        postRepository.save(post);
        postRepository.findMyPost();
        postRepository.delete(post); // 테스트 코드이기 때문에 어차피 롤백이라 무시된다.
        postRepository.flush();
    }

}