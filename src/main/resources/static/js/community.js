// 提交回复
function post() {
    const questionId = $("#question_id").val();
    const content = $(".commentText").val();

    comment2Target(questionId, 1,  content);
}

//二级回复展开效果
function collapseComment(e) {
    const id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);
    if ($(e).hasClass("active")) {//关闭
        comments.removeClass("in");
        $(e).removeClass("active focus");
    } else {  //展开
        if (comments.children().length == 1){
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {

                    const mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<a/>", {
                        "href": "#"
                    })).append($("<img/>", {
                        "class": "media-object index-icon img-rounded",
                        "src": comment.usr.avatarUrl
                    }));

                    const mediaBodyElement = $("<div/>", {
                        "class": "media-body",
                        "style": "padding-top: 3px"
                    }).append($("<h6/>", {
                        "class": "media-heading",
                        html: comment.usr.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<ul/>", {
                        "class": "noneBorder text-desc item nav nav-pills"
                    }).append($("<span/>", {
                        "class": "glyphicon glyphicon-thumbs-up"
                    })).append($("<span/>", {
                        html: comment.likeCount
                    })).append($("<span/>", {
                        "style": "float:right;",
                        html: moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                    })));


                    const mediaElement = $("<div/>", {
                        "class": "media subLine"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    comments.prepend(mediaElement);
                });
            });
        }

        comments.addClass("in");
        $(e).addClass("active");
    }
}

function comment2Target(targetId, type, content) {
    if (!content){
        alert("你还没有写任何内容呢！");
        return;
    }
    $.ajax({
        type : "POST",
        url : "/comment",
        contentType : "application/json",
        data : JSON.stringify({
            "parentId" : targetId,
            "content" : content,
            "type" : type
        }),
        success : function (response) {
            if (response.code === 200) {
                window.location.reload();
            } else if (response.code == 2003){
                const isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=827481f325dcc7ca95ff&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    window.sessionStorage.setItem("closable", true);
                }
            } else {
                alert(response.message);
            }

            console.log(response);
        },
        dataType : "json"
    });
}

//提交二级评论
function comment2Comments(e) {
    const commentId = e.getAttribute("data-id");
    const content = $("#reply-" + commentId).val();
    comment2Target(commentId, 2, content);
}