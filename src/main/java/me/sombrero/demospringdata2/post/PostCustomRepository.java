package me.sombrero.demospringdata2.post;

import java.util.List;

public interface PostCustomRepository {

    List<Post> findMyPost();

}
