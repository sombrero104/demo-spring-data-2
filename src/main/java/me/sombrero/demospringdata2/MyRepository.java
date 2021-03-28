package me.sombrero.demospringdata2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * [2] JpaRepository와 SimpleJpaRepository를 상속받아서 만드는 방법.
 * PostCustomRepository, PostCustomRepositoryImpl 처럼 하나하나 구현체를 만들어서 추가하지 않아도 된다.
 */
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 이 엔티티가 Persistence 컨텍스트에 들어있는지 확인하는 메소드.
     */
    boolean contains(T entity);

}
