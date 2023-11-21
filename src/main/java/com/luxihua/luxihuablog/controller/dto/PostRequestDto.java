package com.luxihua.luxihuablog.controller.dto;

import com.luxihua.luxihuablog.vo.Post;
import lombok.Setter;

@Setter
public class PostRequestDto {
    Integer id;
    String title;
    String content;
    String username;

    public Post getPost() {
        return new Post(this.id, this.title, this.content, this.username);
    }

}
