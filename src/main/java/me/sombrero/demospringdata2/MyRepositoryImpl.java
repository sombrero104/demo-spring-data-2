package me.sombrero.demospringdata2;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * [2] JpaRepository와 SimpleJpaRepository를 상속받아서 만드는 방법.
 * PostCustomRepository, PostCustomRepositoryImpl 처럼 하나하나 구현체를 만들어서 추가하지 않아도 된다.
 */
public class MyRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements MyRepository<T, ID> {

    private EntityManager entityManager;

    public MyRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }

    /**
     * 오버라이딩해서 기본으로 등록되는 JPA의 findAll()이 아닌
     * 커스텀하게 만든 findAll()을 만든다.
     */
    @Override
    public List<T> findAll() {
        return super.findAll();
    }

}
