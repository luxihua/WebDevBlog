package com.luxihua.luxihuablog.service;


import com.luxihua.luxihuablog.mapper.PostMapper;
import com.luxihua.luxihuablog.vo.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    // PostMapper의 findAll을 호출하는 코드 -> findAll 메소드를 찾아서 다시 반환하는 역할만 함
    public List<Post> getAllPost() {
        return postMapper.findAll();
    }

    // findByPage 호출
    public List<Post> getPostByPage(Integer page, Integer size) {
        return postMapper.findByPage(size, (page-1) * size);
    }

    // findOne 호출
    public Post getPostById(Integer id) {
        return postMapper.findOne(id);
    }

    //PostMapper의 save 호출
    public boolean savePost(Post post) {
        Integer result = postMapper.save(post);
        return result == 1;
    }

    // PostMapper의 update 호출
    public boolean updatePost(Post post) {
        Integer result = postMapper.update(post);
        return result == 1;
    }


}
