$(function(){

    $('#more').click(function() {
        // console.log("clicked!");
        // more 버튼을 클릭할 때마다 current-page에 +1 한 값이 next_page에 삽입
        var next_page = parseInt($(this).attr("current-page")) + 1;

        $.ajax({
            method : "GET",
            url : "/post",
            data : {"page" : next_page}
        })

        // 반복문을 돌면서 json arry에서 json 객체를 하나씩 꺼내옴

        .done(function(response) {
            for(var post of response) {
                $("#more-posts").append("<div class=\"post-preview\">" +
                    "<a href=\"/post/" + post.id + "\">" +
                    "<h2 class=\"post-title\">" +
                    post.title +
                    "</h2>\n" +
                    "<h3 class=\"post-subtitle\">" +
                    post.content +
                    "</h3></a><p class=\"post-meta\">Posted by " +
                    post.username +
                    "</p></div><hr class=\"my-4\" />");
                }
            });
           // next_page의 정보를 current_page에 업데이트 시킨다.
            $(this).attr("current-page", next_page);
    });



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

    // 글 수정 버튼 이벤트 처리
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





    // 댓글 기능 이벤트 처리
    $(".comment-edit").hide();

    $(".comment-edit-form-button").click(function(){
        $(this).closest(".comment_text").find(".comment-edit").show();
    });

    $(".comment-edit-cancel-button").click(function(){
        $(this).closest(".comment_text").find(".comment-edit").hide();
    });
});