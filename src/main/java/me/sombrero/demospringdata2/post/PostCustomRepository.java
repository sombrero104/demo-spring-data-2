package me.sombrero.demospringdata2.post;

import java.util.List;

public interface PostCustomRepository<T> {

    List<Post> findMyPost();

    /**
     * JPA에 기본적으로 있는 delete()와 중복이 되지만,
     * 항상 내가 커스텀하게 만든 구현체의 우선순위를 높게 준다.
     * 때문에 delete()를 호출하면 내가 구현한 구현체를 사용하게 된다.
     */
    void delete(T entity);

}
