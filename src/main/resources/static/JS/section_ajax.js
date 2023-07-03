function section_ajax() {
    var obj = {"subject": $("#subject").val()};
    $.ajax({
        url: "/section",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            console.log(result.sections);
            let selectEl = document.querySelector("#section_sel");
            $('#section_sel').children('option:not(:first)').remove();
            var option;
            for (var i = 0;i<result.sections.length;i++){
                option = document.createElement("option");
                option.text = result.sections[i];
                option.value = i+1;
                selectEl.options.add(option);
            }
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

