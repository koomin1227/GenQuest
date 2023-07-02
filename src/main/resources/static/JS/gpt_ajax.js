function gpt_ajax() {
    var obj = {"success": 'ok'};
    $.ajax({
        url: "/quest",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        beforeSend: function() {
                //마우스 커서를 로딩 중 커서로 변경
                $('html').css("cursor", "wait");
        },
        complete: function() {
                //마우스 커서를 원래대로 돌린다
                $('html').css("cursor", "auto");
        },
        success : function(result){
            console.log(result.response);
            $("#gpt_res").text(result.response);
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

