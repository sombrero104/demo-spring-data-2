<br/>

# [스프링 데이터 JPA 2 - 스프링 데이터 Common] 
<br/>

## 커스텀 Repository 만들기
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

## 도메인 이벤트 
도메인 클래스(Post.class와 같은)의 변화를 이벤트로 발생 시킨다. <br/>
그리고 그 이벤트를 리스닝하는 리스너가 그 도메인 클래스의 변화를 감지하고 <br/>
그러한 이벤트 기반의 프로그래밍을 할 수 있다. (어떤 이벤트가 발생했을 때 어떤 리스너의 코드를 실행하도록) <br/>
'도메인 이벤트를 만든다. -> 이벤트를 퍼블리싱한다. -> 이벤트를 리스닝한다.' 이 과정을 직접 매뉴얼할 수 있다.(직접 만들 수 있다.) <br/>
기본적으로 모든 스프링 프레임워크에는 이러한 이벤트 퍼블리싱 기능이 이미 내재되어 있다.  <br/>
왜냐하면 ApplicationContext가 이미 이벤트 퍼블리셔이기 때문이다.  <br/>
<pre>
ApplicationContext extends ApplicationEventPublisher
</pre><br/>

### 1. 커스텀 이벤트, 이벤트 리스너 만들기. 
아래는 커스텀한 이벤트, 이벤트 리스너를 만들어서 이벤트 발생 시 리스너가 잡아서 어떠한 일을 수행하는 것을 만든 것이다. 
<pre>
/**
 * 이벤트를 발생시키는 곳이 Post라고 가정하고
 * ApplicationEvent를 상속받는 이벤트 클래스를 만든다.
 */
public class PostPublishedEvent extends ApplicationEvent {

    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post)source; // 이벤트를 발생시키는 곳이 Post라고 가정.
    }

    public Post getPost() {
        return post;
    }

}
</pre>
<pre>
public class PostListener implements ApplicationListener❮PostPublishedEvent❯ {

    /**
     * 도메인 클래스(Post.class와 같은)의 변화.. 즉 이벤트를 PostPublishedEvent 클래스로 만들고,
     * PostPublishedEvent 이벤트가 발생했을 때 해야할 일들을 이곳에 작성한다.
     */
    @Override
    public void onApplicationEvent(PostPublishedEvent event) {
        System.out.println("-----------------------------------");
        System.out.println(event.getPost().getTitle() + " is published!!");
        System.out.println("-----------------------------------");
    }

}
</pre>
<pre>
/**
 * 이벤트 리스너 테스트(PostRepositoryTest)를 위해
 * PostPublishedEvent 이벤트(도메인 클래스(Post.class와 같은)의 변화)를 감지하는
 * 커스텀 리스너인 PostListener를 빈으로 등록해준다.
 * PostRepositoryTest에서는 '@Import(PostRepositoryTestConfig.class)'해서 사용한다.
 */
@Configuration
public class PostRepositoryTestConfig {

    @Bean
    public PostListener postListener() {
        return new PostListener();
    }

}
</pre>
<pre>
@DataJpaTest
@Import(PostRepositoryTestConfig.class)
class PostRepositoryTest {

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
    ...
}
</pre>
=> PostPublishedEvent, PostListener, PostRepositoryTestConfig. PostRepositoryTest 참조. 
<br/><br/><br/><br/>

### 2. 스프링 데이터의 도메인 이벤트 Publisher
스프링 데이터에서 이벤트 자동 퍼블리싱 기능을 제공해준다. (현재는 Repository를 save()할 때만 발생한다.) <br/>
이벤트를 모아뒀다가 어떠한 엔티티에 쌓여있던 이벤트를 save()를 하는 순간 다 보내준다. <br/>
이벤트를 모아놓는 곳이 @DomainEvents라는 애노테이션을 가지고 있는 메소드이고, <br/>
이벤트를 다 보낸 다음 컬렉션에 있던 이벤트를 다 비우는데 자동으로 비워주는 메소드가 @AfterDomainEventPublication 이 붙은 메소드이다. <br/>
즉, @DomainEvents는 이벤트를 모아놓는 곳, @AfterDomainEventPublication는 이벤트를 비우는 곳이다. <br/>
이 두개의 메소드를 구현해야 하는데, 직접 구현할 필요없이 미리 구현해 놓은 AbstractAggregateRoot<E>를 (상속받아서) 사용하면 된다. <br/>
이렇게 사용하면 위에서 처럼 우리가 직접 ApplicationContext에 이벤트를 던지는 코딩을 할 필요가 없어진다. <br/>
<pre>
@Entity
public class Post extends AbstractAggregateRoot❮Post❯ {
    ...
    /**
     * 이벤트를 만들어서 registerEvent()로 이벤트를 등록한다.
     */
    public Post publish() {
        this.registerEvent(new PostPublishedEvent(this));
        return this;
    }
}
</pre>
<pre>
@Test
public void crud() {
    ...
    postRepository.save(post.publish()); // save를 하면 모아져있던 이벤트를 다 발생시킨다.
    ...
}
</pre>
<br/><br/><br/><br/>

# QueryDSL
<br/>

### 여러 쿼리 메소드는 대부분 두 가지 중 하나이다. (QuerydslPredicateExecutor 인터페이스 참조.)
- Optional❮T❯ findOne(Predicate): 이런 저런 조건으로 무언가 하나를 찾는다.
- List❮T❯|Page❮T❯ findAll(Predicate): 이런 저런 조건으로 무언가 여러개를 찾는다.
<br/>
