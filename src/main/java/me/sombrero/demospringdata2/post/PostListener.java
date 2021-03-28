package me.sombrero.demospringdata2.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

/**
 * 이벤트 리스너를 만드는 두가지 방법.
 * 1. ApplicationListener를 구현하여 onApplicationEvent() 메소드를 오버라이딩 한다. (이벤트 발생 시 실행할 코드를 작성하는 메소드.)
 * 2. 이벤트 발생 시 실행할 코드를 작성하는 메소드에 @EventListener 애노테이션을 붙여준다.
 * 리스너를 만든 후 빈으로 등록해줘야 한다. (여기서는 PostRepositoryTestConfig에서 빈으로 등록하고 있다.)
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
        System.out.println(event.getPost().getTitle() + " is published!!");
        System.out.println("-----------------------------------");
    }

}
