## Paging 구현

---------------------------    

### 더보기 기능 추가하기

<br>

1. Mapper 개발


     - PostMapper.java 클래스 파일 수정 추가
     
     ``` Java
     List<Post> findByPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
     ```
     
     - post-mapper.xml 쿼리문 추가
     
     ``` xml
     <select id="findByPage" resultType = "com.luxihua.luxihuablog.vo.Post">
        SELECT *
        FROM post
        ORDER BY id DESC
        LIMIT #{limit} OFFSET #{offset}
    </select>
    ```
    
    
    -> 여기서 #limit과 #offset 이 페이지의 끝과 시작을 나타냄
    
    
    
2. Service 개발

    
    ``` Java
    public List<Post> getPostByPage(Integer page, Integer size) {
        return postMapper.findByPage(size, (page-1) * size); }
    ```
    
    
    -> size이 post-mapper.xml 에서는 limit과  , (page-1) * size이 offset과 매칭됨
    
    
    
3. IndexController.java 파일 수정 및 추가
    
    
    ``` Java
    List<Post> postList = postService.getAllPost();
    ```
    
    
    -> 이 부분을 수정하여서 첫 화면에서 블로그 글이 3개씩만 보이도록 수정해야함.
    
    
    ``` Java
    @RequestMapping(value = "/")
        public String index(Model model) {
            // 1페이지에 3개씩 보여주도록 postList 객체에 반환
            List<Post> postList = postService.getPostByPage(1, 3);
            model.addAttribute("posts", postList);
            return "index";
        }
    ```
    
    
    
4. DTO 구현

    
   >  REST API에서 한 page씩 요청 데이터나 응답 데이터를 json 형식으로 반환하려면 DTO로 넘겨주고 받아와야함.
   >> 이때 Lombok 라이브러리를 활용하여 자동 Getter, Setter 등 java 문법을 사용할 수 있음
    
    
    
    -  PostRequestDTO
    
    
    ``` Java
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
    ```

    
    - PostResponseDTO
    
   
   
``` Java
    package com.luxihua.luxihuablog.controller.dto;
    import com.luxihua.luxihuablog.vo.Post;
    import lombok.Getter;

    @Getter
    public class PostResponseDto {

        Integer id;
        String title;
        String content;
        String username;

        public PostResponseDto(Post post) {
            this.id = post.getId();
            this.title=post.getTitle();
            this.content= post.getContent();
            this.username= post.getUsername();
        }

    }
```
    
    
5. PostController.java 파일 수정 및 추가
    
    
    ``` Java
    
    @GetMapping("/post")
    public List<PostResponseDto> getPostList(@RequestParam Integer page) {
        List<Post> postList = postService.getPostByPage(page, 3);

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post : postList) { // postList 객체에서 하나씩 post을 꺼내온다.
            postResponseDtoList.add(new PostResponseDto(post));
        }

        return postResponseDtoList;
    }
    ```
    
    > postList 객체는 처음 3개로 고정
    >> 새로운 ArrayList로 postResponseDtoList을 선언
    >>> for문을 돌면서 postList 객체에서 하나씩 post 변수로 꺼내온다
    >>>> post를 postResponseDtoList에 하나씩 추가하여 3개씩 갯수를 맞춘다
    
    -> 최종 postResponseDtoList 반환


---------------------------    
    
    
## 상세보기 구현

<br>


1. Mapper 개발

    - PostMapper.java 클래스에 인터페이스 추가
    
    
    ``` Java
    Post findOne(@Param("id) Integer id);
    ```
    
    
    - post-mapper.xml 쿼리문 추가
    
    ``` xml

    <select id="findOne" resultType="com.luxihua.luxihuablog.vo.Post">
        SELECT *
        FROM post
        WHERE id=#{id};
    </select>
   
   ```
   
   
2. Service 개발

    - 메소드 호출하여 findOne에서 가져온 postMapper 객체 반환하기
    
    ``` Java
    public Post getPostById(Integer id) {
           return postMapper.findOne(id);
       }
    ```
    
    
3. Controller 개발

    - PostPageController.java 클래스 파일 추가
    
    ``` Java
    
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

    }
    ```
    
    -> return이 post_detail.mustache로 반환됨
   
   
   
---------------------------------

## Blog 글 저장 기능 개발



-  Backend


    1. Mapper 개발
    
        - PostMapper.java 클래스 파일 추가
        
            ``` Java
            Integer save(@Param("post) Post post);
            ```    
            
            -> save 성공 시 성공한 row number == 1로 반환됨
            
            
        - post-mapper.xml 쿼리문 추가
        
            ``` xml
            <insert id="save">
                INSERT INTO post(title, content, username)
                VALUES (#{post.title), #{post.content}, #{post.username});
            </insert>
            ```
            
    
    2. Service 개발
    
        - PostService.java 클래스 파일에 PostMapper의 save 메서드 호출
        
        ``` Java
        public boolean savePost(Post post) {
            Integer result = postMapper.save(post);
            return result == 1;
            }
        ```
    
    
    3. Controller 개발
    
        - PostController.java 클래스 수정
        
            > PostController.java 클래스 파일에 /post 경로로 PostMapping 요청
            >> getPost()로 postDTO의 값을 읽어와 post 객체에 저장
            >>> 해당 post 객체를 매개변수로 Service 단계의 savePost를 호출
        
        
            ``` Java
            @PostMapping("/post")
               public String createPost(@RequestBody PostRequestDto postDto) {
                   Post post = postDto.getPost();
                   postService.savePost(post);

                   return "success";
               }
            ```
            
            -> API 호출 결과 success 반환
            
            
            
        - 글 추가 화면을 위한 PostPageController.java 클래스 수정
        
        ``` Java
        
        @RequestMapping("/post/create")
        public String getPostCreatePage() {
            return "post_write";
        }
        
        ```
        
        -> post_write.mustache 파일을 반환 및 /post/create 경로로 이동
        
        
        

- Frontend


    1. '등록' 버튼 클릭 이벤트 처리
    
    2. 버튼 클릭 시 form에 작성 된 데이터를 가져옴
    
    3. ajax로 REST API 호출
    
    4. API 정상 응답 시 index 화면으로 이동(redirect)
    
    
    ``` js
    
    // 글 등록 버튼에 대한 이벤트 처리
    $("#create_button").click(function() {
        var title = $("#post-title").val();
        var username = $("#post-username").val();
        var content = $("#post-content").val();
     // 글을 작성하면 val을 통해서 가져와서 각각의 변수에 저장

        $.ajax({
            method : "POST",
            url : "/post",
            data : JSON.stringify({
                "title" : title,
                "username" : username,
                "content" : content
            }),
            contentType: "application/json"
           })
           .done(function(response) {
                console.log("Post creation success!");
                window.location.href = "/"; // redirect 문과 동일, 상위 링크로 보냄
           });
    });
    
    ```



## Blog 글 수정 기능 개발


- Backend


    1. 글 수정 REST API를 위한 Mapper 개발
    
        - PostMapper.java 클래스 파일에 글 수정 인터페이스 추가
        
        ``` Java
        Integer update(@Param("post") Post post);
        ```
        
        -> 글 수정이 성공하면 row number == 1이 반환됨.
        
    
    
    2. 글 수정 REST API를 위한 Service 개발
    
        - PostService.java 클래스 파일에 update 메서드를 호출하는 코드 추가
        
        
        ``` Java
        public boolean updatePost(Post post) {
              Integer result = postMapper.update(post);
              return result == 1;
          }
          ```
          
        
    4. 글 수정 REST API를 위한 Controller 개발
    
        - PostControler.java 클래스 파일 수정
            > PostController.java 클래스 파일에 /post 경로로 PutMapping 요청
            >> getPost()로 postDTO의 값을 읽어와 post 객체에 저장
            >>> 해당 post 객체를 매개변수로 Service 단계의 updatePost를 호출
    
    
        ``` Java
        
        @PutMapping("/post")
        public String updatePost(@RequestBody PostRequestDto postDto) {
            Post post = postDto.getPost();
            postService.updatePost(post);

            return "success";
        }
        
        ```
        
        -> API 호출 결과 success 반환
        
        
        - 글 수정 화면을 위한 PostPageController.java 클래스 파일 수정
        
            > 수정 Form에 기존에 작성된 제목, 본문 데이터를 노출하기 위해 
            >> post 객체 조회 후 model에 세팅
            >>> post_edit.mustache 파일 반환 및 /post/edit/{id} 로 경로 지정
      
      
            
            ``` Java
            
            @RequestMapping("/post/edit/{id}")
            public String getPostCreatePage(Model model, @PathVariable Integer id) {
                // 수정 Form에 기존에 작성 된 제목, 본문 데이터를 노출하기 위해
                // post 객체 조회 후 model에 세팅
                Post post = postService.getPostById(id);
                model.addAttribute("post", post);
                return "post_edit";
          ```
          
          
            
    
- Frontend

    1. '수정' 버튼 클릭 시 이벤트 처리
    
    2. 버튼 클릭 시 form에 작성 된 데이터를 가져옴
    
    3. ajax로 REST API 호출
    
    4. API 정상 응답 시 글 상세 화면으로 이동(redirect)



    ``` js
    
    $("#edit_button").click(function() {
           var id = $("#edit-post-id").val();
           var title = $("#edit-post-title").val();
           var content = $("#edit-post-content").val();

           $.ajax({
               method : "PUT",
               url: "/post",
               data: JSON.stringify({
                   "id" : id,
                   "title" : title,
                   "content" : content
               }),
               contentType : "application/json"
           })

           .done(function(response) {
               console.log("Post creation success!");
               // id 정보를 사용하여 수정 한 글의 상세 조회 화면으로 이동(Redirect)
               window.location.href = "/post/" + id;
           });
       });
       
    ```
    
    > 글 상세 페이지에서 "수정" 버튼 클릭 시 수정 페이지 진입
    >> 글 입력 Form에 기존 작성 된 데이터 노출(제목, 본문)
    >>> 데이터 수정 후 "수정" 버튼 클릭 시 DB 저장 후 글 상세 페이지로 Redirect




    
    
    
    

    
        
    




 
 
