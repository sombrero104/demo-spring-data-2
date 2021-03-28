package me.sombrero.demospringdata2.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

/**
 * 이벤트 리스너를 만드는 방법.
 * 1. ApplicationListener를 구현하여 onApplicationEvent() 메소드를 오버라이딩 한다. (이벤트 발생 시 실행할 코드를 작성하는 메소드.)
 * 2. 이벤트 발생 시 실행할 코드를 작성하는 메소드에 @EventListener 애노테이션을 붙여준다.
 * 3. 리스너를 클래스로 만들고 싶지 않을 경우에는 리스너를 빈으로 등록하는 PostRepositoryTestConfig에서 구현하는 방법도 있다.
 *
 * 리스너를 만든 후 빈으로 등록해줘야 한다. (여기서는 PostRepositoryTestConfig에서 빈으로 등록하고 있다.)
 * 리스너에 @Component를 붙이지 않고 PostRepositoryTestConfig에서 빈으로 등록한 이유는..
 * PostRepositoryTest에서 테스트 할때 테스트가 기본적으로 슬라이싱 테스트이기 때문에 @Component로 등록된 빈을 인식하지 못한다.
 * (@DataJpaTest 테스트이기 때문에 @Repository로 붙이면 빈으로 인식할 수는 있지만 리파지토리가 아니기 때문에 좋은 방법이 아니다.)
 * 때문에 직접 빈으로 등록해주고 @Import(PostRepositoryTestConfig.class)로 임포트해서 쓰는 방식을 사용했다.
 */
public class PostListener { // implements ApplicationListener<PostPublishedEvent> {

    /**
     * 도메인 클래스(Post.class와 같은)의 변화.. 즉 이벤트를 PostPublishedEvent 클래스로 만들고,
     * PostPublishedEvent 이벤트가 발생했을 때 해야할 일들을 이곳에 작성한다.
     */
    // @Override
    @EventListener // ApplicationListener를 구현하지 않고 @EventListener를 붙여줘도 된다.
    public void onApplicationEvent(PostPublishedEvent event) {
        System.out.println("-----------------------------------");
        System.out.println("##### [1] " + event.getPost().getTitle() + " is published!!");
        System.out.println("-----------------------------------");
    }

}
