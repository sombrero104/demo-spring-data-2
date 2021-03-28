package me.sombrero.demospringdata2.post;

import org.springframework.context.ApplicationEvent;

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
