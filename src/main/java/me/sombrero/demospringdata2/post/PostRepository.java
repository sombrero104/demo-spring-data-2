package me.sombrero.demospringdata2.post;

import me.sombrero.demospringdata2.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 커스텀하게 만든 리파지토리인 PostCustomRepository를 상속받으면
 * 구현체인 PostCustomRepositoryImpl의 기능도 추가된다.
 */
// public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {
public interface PostRepository extends MyRepository<Post, Long> {
}
