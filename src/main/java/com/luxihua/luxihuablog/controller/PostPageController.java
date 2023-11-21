package com.luxihua.luxihuablog.controller;


import com.luxihua.luxihuablog.service.PostService;
import com.luxihua.luxihuablog.vo.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostPageController {

    private PostService postService;

    public PostPageController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping("/post/{id}")
    public String getPostDetailPage(Model model, @PathVariable Integer id) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post_detail"; // mustache >> post_detail.mustache 파일로 리턴
    }

    @RequestMapping("/post/create")
    public String getPostCreatePage() {
        return "post_write";
    }

    @RequestMapping("/post/edit/{id}")
    public String getPostCreatePage(Model model, @PathVariable Integer id) {
        // 수정 Form에 기존에 작성 된 제목, 본문 데이터를 노출하기 위해
        // post 객체 조회 후 model에 세팅
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post_edit";

    }

}
