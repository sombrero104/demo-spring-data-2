package me.sombrero.demospringdata2.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

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
