package com.luxihua.luxihuablog.controller;

import com.luxihua.luxihuablog.controller.dto.PostRequestDto;
import com.luxihua.luxihuablog.controller.dto.PostResponseDto;
import com.luxihua.luxihuablog.service.PostService;
import com.luxihua.luxihuablog.vo.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    //PostService 의존성 주입
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public List<PostResponseDto> getPostList(@RequestParam Integer page) {
        List<Post> postList = postService.getPostByPage(page, 3);

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post : postList) { // postList 객체에서 하나씩 post을 꺼내온다.
            postResponseDtoList.add(new PostResponseDto(post));
        }

        return postResponseDtoList;
    }

    @PostMapping("/post")
    public String createPost(@RequestBody PostRequestDto postDto) {
        Post post = postDto.getPost();
        postService.savePost(post);

        return "success";
    }

    @PutMapping("/post")
    public String updatePost(@RequestBody PostRequestDto postDto) {
        Post post = postDto.getPost();
        postService.updatePost(post);

        return "success";
    }
}
