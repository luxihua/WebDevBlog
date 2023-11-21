package com.luxihua.luxihuablog.controller;


import com.luxihua.luxihuablog.service.PostService;
import com.luxihua.luxihuablog.vo.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    // PostService을 사용할 수 있도록 의존성 주입
    private PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "/")
    public String index(Model model) {
        // 1페이지에 3개씩 보여주도록 postList 객체에 반환
        List<Post> postList = postService.getPostByPage(1, 3);
        model.addAttribute("posts", postList);
        return "index";
    }
}
