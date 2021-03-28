package me.sombrero.demospringdata2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 이 엔티티가 Persistence 컨텍스트에 들어있는지 확인하는 메소드.
     */
    boolean contains(T entity);

}
