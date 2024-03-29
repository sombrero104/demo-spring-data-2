package me.sombrero.demospringdata2.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * [1] 하나하나 구현체를 만들어서 사용하는 방법.
 */
@Repository
@Transactional
public class PostCustomRepositoryImpl implements PostCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Post> findMyPost() {
        System.out.println("##### Custom findMyPost!!");
        return entityManager.createQuery("SELECT p FROM Post AS p", Post.class).getResultList();
    }

    @Override
    public void delete(Object entity) {
        System.out.println("##### Custom delete!!");
        entityManager.remove(entity);
    }

}
