package com.luxihua.luxihuablog.mapper;


import com.luxihua.luxihuablog.vo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    // 모든 목록 조회 인터페이스 추가
    List<Post> findAll();

    // 페이징 인터페이스 추가
    List<Post> findByPage(@Param("limit") Integer limit, @Param("offset") Integer offset);

    // 글 상세보기 인터페이스 추가
    Post findOne(@Param("id") Integer id);

    // 글 저장 인터페이스 추가 -> 글 저장 성공하면 row number = 1 반환
    Integer save(@Param("post") Post post);

    // 글 수정 인터페이스 추가 -> 글 수정 성공하면 row number = 1 반환
    Integer update(@Param("post") Post post);


}
