function post() {
    const questionId = $("#question_id").val();
    const content = $(".commentText").val();
    $.ajax({
        type : "POST",
        url : "/comment",
        contentType : "application/json",
        data : JSON.stringify({
            "parentId" : questionId,
            "content" : content,
            "type" : 1
        }),
        success : function (response) {
            if (response.code === 200) $("#comment_section").hide();
            else if (response.code == 2003){
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