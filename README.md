# 스프링 데이터 JPA 2
<br/>

### 1. 커스텀 Repository 만들기.  
### (커스텀 Repository를 매번 하나하나씩 추가로 만들어서 사용해야 하는 방법.)
기본 JPA Repository의 기능 중 특정 기능을 사용하고 싶지 않은 경우, 내가 직접 만들고 싶은 경우.  
<pre>
public interface PostCustomRepository❮T❯ {
    ...
    void delete(T entity); // 항상 기본 JPA 리파지토리의 기능보다 내가 만든 커스텀 리파지토리 기능이 우선순위가 높다. 
}
</pre>
<pre>
@Repository
@Transactional
public class PostCustomRepositoryImpl implements PostCustomRepository {
    @Autowired
    EntityManager entityManager;
    
    @Override
    public void delete(Object entity) {
        ...
    }
}
</pre>
<pre>
public interface PostRepository extends JpaRepository❮Post, Long❯, PostCustomRepository❮Post❯ {
    // JpaRepository, PostCustomRepository를 상속받는다. 
    // 항상 기본 JPA 리파지토리의 기능보다 내가 만든 커스텀 리파지토리 기능이 우선순위가 높다. 
}
</pre>
=> PostCustomRepository, PostCustomRepositoryImpl, PostRepository 참조.
<br/><br/><br/><br/>

### 2. 기본 Repository 커스터마이징.  
### (커스텀 Repository를 하나만 만들어서 사용하는 방법.)
위에서 처럼 매번 커스텀 Repository 인터페이스와 구현체를 새로 만들지 않고, <br/>
아예 JpaRepository와 JpaRepository의 기본 구현체인 SimpleJpaRepository를 상속받는<br/>
커스텀 Repository 인터페이스와 구현체를 직접 하나만 만들어서 사용하는 방법이다. <br/>
<pre>
@NoRepositoryBean
public interface MyRepository❮T, ID extends Serializable❯ extends JpaRepository❮T, ID❯ {

    boolean contains(T entity);

}
</pre>
<pre>
public class MyRepositoryImpl❮T, ID extends Serializable❯
        extends SimpleJpaRepository❮T, ID❯
        implements MyRepository❮T, ID❯ {
        
        private EntityManager entityManager;
        
        public MyRepositoryImpl(JpaEntityInformation❮T, ?❯ entityInformation, EntityManager entityManager) {
            super(entityInformation, entityManager);
            this.entityManager = entityManager;
        }
        
        @Override
        public boolean contains(T entity) {
            ...
        }
}
</pre>
<pre>
public interface PostRepository extends MyRepository❮Post, Long❯ {
}
</pre>
=> MyRepository, MyRepositoryImpl, PostRepository 참조. 
<br/><br/><br/><br/>


<br/><br/><br/><br/>