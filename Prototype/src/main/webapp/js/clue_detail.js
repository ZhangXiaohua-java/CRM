$(function () {

    addRemark();
    correlationActivityWithClue();
    correlationClueWithActivity();
    bindingEvent();
    deleteEvent();
    convert();
    deleteRemark();
})

//添加备注,明日验证,今天到此为止
function addRemark() {
    $("#saveRemarkBtn").click(function () {
        var noteContent = $("#remark").val();
        var clueId = $("#remark").attr("clueId");
        $.ajax({
            url:"/workbench/clue/saveClueRemark",
            type:"post",
            data:{
                noteContent:noteContent,
                clueId:clueId
            },
            dataType:"json",
            success:function (data) {
                if (data.responseStatus == "200"){
                    var html = "";
                    var activityName = $("#clueName").text();
                    var info = data.map.clueRemark.createTime+"创建";
                    if (data.map.clueRemark.editFlag == "1"){
                        info = data.map.clueRemark.editTime+"修改";
                    }
                    html += "<div class='remarkDiv' style='height: 60px;' id='div_"+data.map.clueRemark.id+"'>";
                    html += "<img title='"+data.map.clueRemark.createBy+"' src='/image/user-thumbnail.png' style='width: 30px; height:30px;'>";
                    html += "<div style='position: relative; top: -40px; left: 40px;' >";
                    html += "<h5 class='content'>"+noteContent+"</h5>";
                    html += "<font color='gray'>线索</font> <font color='gray'>-</font> <b>"+activityName+"</b> <small id='small_"+data.map.clueRemark.id+"' style='color: gray;'>"+info+"</small>";
                    html += " <div style='position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;'>";
                    html += "<a class='myHref' fun='edit' remarkId='"+data.map.clueRemark.id+"' href='javascript:void(0);'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: #E6E6E6;'></span></a>";
                    html += " &nbsp;&nbsp;&nbsp;&nbsp;";
                    html += "<a class='myHref' fun='del' remarkId='"+data.map.clueRemark.id+"' href='javascript:void(0);'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: #E6E6E6;'></span></a>";
                    html += "       </div>\n" +
                        "                </div>\n" +
                        "            </div>";
                    $("#remarkDiv").before(html);
                    $("#remark").val("");
                }else{
                    alert("系统忙,请稍候");
                }
            }
        })
    })

}

//关联市场活动

correlationActivityWithClue = ()=>{
    $("#correlationActivityBtn").click(function () {
        $("#bundModal").modal("show");
    })
    $("#queryActivityNameCondition").keyup(function () {
        var condition = $.trim($(this).val());
        if (condition.length ==0 || condition == ""){
            return;
        }
        var clueId = $("#remark").attr("clueId");
    //    向后台发起根据市场活动名查询市场活动的异步请求

        $.ajax({
            url: "/workbench/activity/queryActivityWithActivityName",
            data:{activityName:condition,
                  clueId:clueId
            },
            dataType: "json",
            type: "get",
            success:function (data) {
                $("#queryActiviytBody").html("");
                var html = "";
                if(data.responseStatus == "200"){
                //   遍历后端返回的结果
                    $.each(data.map.activityList,function(index,element){
                        html+= "<tr id='"+element.id+"'>" ;
                        html+=  "<td><input type='checkbox' value='"+element.id+"'/></td>"
                        html+=  "<td>"+element.name+"</td>";
                        html+=  "<td>"+element.startDate+"</td>";
                        html+=  "<td>"+element.endDate+"</td>";
                        html+=  "<td>"+element.owner+"</td>";
                        html+= "</tr>";
                    })
                    $("#queryActiviytBody").html(html);
                    return;
                }
                $("#queryActiviytBody").html("什么也没有查到");

            }
        })

    })

}

// 关联线索与市场活动之间的关系

function correlationClueWithActivity() {
    $("#correlationBtn").click(function () {
    //    获取所有被选中的市场活动信息
        var acvityList = $("#queryActiviytBody input[type='checkbox']:checked");
        if (acvityList.size() ==0){
            alert("请选择要绑定的市场活动信息");
            return;
        }
        var clueId = $("#remark").attr("clueId");
        var array = new Array(acvityList.size());
        $.each(acvityList,function (index, element) {
            array[index] = element.value;
        })
        $.ajax({
            url:"/workbench/clue/saveAssociationBetweenClueAndActivity",
            type:"post",
            data:{
                activityId:array,
                clueId:clueId
            },
            dataType:"json",
            success:function (data) {
                if (data.responseStatus == "200"){
                //    更新线索的关联活动信息
                    var html = "";
                    $("#activityBody").html(html);
                    $.each(data.map.activityList,function(index,element){
                        html+= "<tr id='"+element.id+" class='functionClass' '>" ;
                        html+=  "<td><input type='checkbox' value='"+element.id+"'/></td>";
                        html+=  "<td>"+element.name+"</td>";
                        html+=  "<td>"+element.startDate+"</td>";
                        html+=  "<td>"+element.endDate+"</td>";
                        html+=  "<td>"+element.owner+"</td>";
                        html+= "<td><a href='javascript:void(0);' class='unbindingEle' activityId='"+element.id+"' style='text-decoration: none;'><span class='glyphicon glyphicon-remove'></span>解除关联</a></td>"
                        html+= "</tr>";
                    });
                    $("#activityBody").html(html);
                    $("#bundModal").modal("hide");
                    return;
                }else{
                    alert("系统忙,请稍候...");
                }
            }
        })

    })
}

// 为备注后面的修改和删除图标添加事件
bindingEvent = ()=>{
    // $(".remarkDiv").mouseover(function(){
    //     $(this).children("div").children("div").show();
    // });
    //
    $("#remarkBody").on("mouseover",".remarkDiv",function(){
        $(this).children("div").children("div").show();

    })

    // $(".remarkDiv").mouseout(function(){
    //     $(this).children("div").children("div").hide();
    // });

    $("#remarkBody").on("mouseout",".remarkDiv",function () {
        $(this).children("div").children("div").hide();

    })

    $("#remarkBody").on("mouseover",".myHref",function () {
        $(this).children("span").css("color","red");
    });


    // $(".myHref").mouseover(function(){
    //     $(this).children("span").css("color","red");
    // });

    $("#remarkBody").on("mouseout",".myHref",function () {
        $(this).children("span").css("color","#E6E6E6");
    })


    // $(".myHref").mouseout(function(){
    //     $(this).children("span").css("color","#E6E6E6");
    // });
}

function deleteEvent() {

    $("#activityBody").on("click",".unbindingEle",function (obj) {
        var activityId = $(this).attr("activityId");
        var clueId = $("#remark").attr("clueId");
        if (!confirm("是否确认删除")){
            return;
        }
        console.log(activityId);
        console.log(clueId);
        $.ajax({
            url:"/workbench/clue/unbindingAssociation",
            type:"delete",
            data:{
                activityId:activityId,
                clueId:clueId
            },
            dataType:"json",
            success:function (data) {
                if (data.responseStatus == "200"){
                    $(this).parent("td").parent("tr .functionClass").remove();

                }
            }
        })

    })


}

// 线索转换

function convert() {
    $("#convertBtn").click(function () {
        var  clueId = $("#remark").attr("clueId");
        window.location.href = "/workbench/clue/queryClue/"+clueId;
    })

}

//删除备注事件
function deleteRemark() {
    $("#remarkBody").on("click",".myHref[fun='del']",function (obj) {
    //   发起异步请求删除备注
        var remarkId = $(this).attr("remarkId");
       $.ajax({
           url:"/workbench/clue/deleteRemark/"+remarkId,
           type:"delete",
           dataType:"json",
           success:function (data) {
               if(data.responseStatus == "200"){
               //    删除父节点div,不需要发请求刷新页面
                   $("#div_"+remarkId).remove();
               }else{
                   alert("系统忙,请稍候...");
               }
           }
       })

    })
}

//修改备注
function modifyRemark() {
    $("div").on("click",".myHref[fun='edit']",function () {
    //    功能类似,不写了,无非就是获取修改后的内容发送到后台,动态地替换元素的内容

    })

}