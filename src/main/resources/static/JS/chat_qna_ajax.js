function chat_qna_ajax() {
    var qnaDiv = document.getElementById("qna_section");
    var data=$("#input").val();
    var obj = {"quest": data};
    $('#qna_section').append('<div class="request"><p class="question" id="quest">'+ data +'</p></div>');
    qnaDiv.scrollTop = qnaDiv.scrollHeight;
    $("#input").val('')
    $.ajax({
        url: "/quest_sp",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            console.log(result.response);
            $('#qna_section').append('<div class="response"><p class="question" id="response">'+ result.response +'</p></div>');
            qnaDiv.scrollTop = qnaDiv.scrollHeight;
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

function chat_qna_ajax_qu() {
    var qnaDiv = document.getElementById("qna_section");
    var data=$("#input").val();
    var obj = {"quest": data};
    $('#qna_section').append('<div class="request"><p class="question" id="quest">'+ data +'</p></div>');
    qnaDiv.scrollTop = qnaDiv.scrollHeight;
    $("#input").val('')
    $.ajax({
        url: "/quest_qu",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            console.log(result.response);
            $('#qna_section').append('<div class="response"><p class="question" id="response">'+ result.response +'</p></div>');
            qnaDiv.scrollTop = qnaDiv.scrollHeight;
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

function chat_qna_ajax_ex() {
    var qnaDiv = document.getElementById("qna_section");
    var data=$("#input").val();
    var obj = {"quest": data};
    $('#qna_section').append('<div class="request"><p class="question" id="quest">'+ data +'</p></div>');
    qnaDiv.scrollTop = qnaDiv.scrollHeight;
    $("#input").val('')
    $.ajax({
        url: "/quest_ex",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            console.log(result.response);
            $('#qna_section').append('<div class="response"><p class="question" id="response">'+ result.response +'</p></div>');
            qnaDiv.scrollTop = qnaDiv.scrollHeight;
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

