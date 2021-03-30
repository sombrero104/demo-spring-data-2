package me.sombrero.demospringdata2.post;

import me.sombrero.demospringdata2.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 커스텀하게 만든 리파지토리인 PostCustomRepository를 상속받으면
 * 구현체인 PostCustomRepositoryImpl의 기능도 추가된다.
 *
 * [QuerydslPredicateExecutor]
 * QueryDSL을 사용하기 위해 QuerydslPredicateExecutor를 상속받도록 한다.
 * QuerydslPredicateExecutor를 각 도메인 별 리파지토리 인터페이스가 상속 받게하면
 * QuerydslPredicateExecutor의 확장 구현체인 QuerydslJpaPredicateExecutor를
 * 프레그먼트로 쓰도록 된다.
 */
// public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {
public interface PostRepository extends MyRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
}
