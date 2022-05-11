$(function () {

    searchActivity();
    convert();
    bindingClickEvent();
})

//模糊查询已经和线索关联的市场活动信息

function searchActivity() {
    $("#searchActivity").click(function () {
        $("#searchActivityModal").modal("show");
    })
    //检索市场活动信息
    $("#conditionTxt").keyup(function () {
        var clueId = $("#activityTable").attr("clueId");
        var condition = this.value;
    //    发起异步查询请求
        var activityBody = $("#activityBody");
        $.ajax({
            url:"/workbench/clue/fuzzyQueryActivity",
            data:{
                activityName:condition,
                clueId:clueId
            },
            dataType:"json",
            success:function (data) {
                if (data.responseStatus == "200"){
                    var html = "";
                    $.each(data.map.activityList,function (index,element) {
                        html += "<tr>";
                        html +=    "<td><input type='radio' value='"+element.id+"' activityName='"+element.name+"' name='activity'/></td>"
                        html +=    "<td>"+element.name+"</td>";
                        html +=   " <td>"+element.startDate+"</td>";
                        html +=    "<td>"+element.endDate+"</td>";
                        html +=    "<td>"+element.owner+"</td>"
                        html +=   " </tr>";
                    })
                    activityBody.html(html);
                }else{
                    activityBody.html("什么也没有查到");
                }

            }
        })

    })
}

//当选中要绑定的市场活动后将关键信息记录到按钮上
function bindingClickEvent() {

    $("#activityBody").on("click","input[type='radio']",function () {
         $("#activity").val($(this).attr("activityName"));
         $("#convertBtn").attr("activityId",this.value);
        $("#searchActivityModal").modal("hide");
    })

}

//线索转换
function convert(){
    $("#convertBtn").click(function () {
        var clueId = $("#activityTable").attr("clueId");
        var traninfo = $("#tranInfo").serialize();
        var activityId = $("#convertBtn").attr("activityId");
        var tranFlag = $("#isCreateTransaction").prop("checked");
        var data = traninfo+"&clueId="+clueId+"&tranFlag="+tranFlag+"&activityId="+activityId;
        console.log(data)
        $.ajax({
            url: "/workbench/clue/convert",
            data:data,
            type:"get",
            dataType: "json",
            success:function (data) {
                console.log(data);
                window.location.href = "https://www.baidu.com";
            }
        })

    })

}