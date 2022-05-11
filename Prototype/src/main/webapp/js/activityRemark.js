$(function () {
    view();
    addActivityRemark();
    deleteEvent();
    modifyEvent();
    modifyRemark();
})

//添加市场活动备注

addActivityRemark = ()=>{
    $("#addRemarkBtn").click(()=>{
       var conment = $.trim($("#remark").val());
       if (!conment){
           alert("评论内容不能为空");
           return;
       }
       var activityid = $("#addRemarkBtn").attr("activityid");
       $.ajax({
           url:"/workbench/activity/addActivityRemark",
           data:{
               activityId:activityid,
               noteContent:conment
           },
           type:"post",
           dataType:"json",
           success:(data)=>{
               if (data.responseStatus == "200"){
                   var html = "";
                   var activityName = $("#activityName").text();
                   var info = data.map.activityRemark.createTime+"创建";
                   if (data.map.activityRemark.editFlag == "1"){
                       info = data.map.activityRemark.editTime+"修改";
                   }
                    html += "<div class='remarkDiv' style='height: 60px;' id='"+data.map.activityRemark.id+"'>";
                    html += "<img title='"+data.map.activityRemark.createBy+"' src='/image/user-thumbnail.png' style='width: 30px; height:30px;'>";
                    html += "<div style='position: relative; top: -40px; left: 40px;' >";
                    html += "<h5 class='content'>"+conment+"</h5>";
                    html += "<font color='gray'>市场活动</font> <font color='gray'>-</font> <b>"+activityName+"</b> <small id='small_"+data.map.activityRemark.id+"' style='color: gray;'>"+info+"</small>";
                    html += " <div style='position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;'>";
                    html += "<a class='myHref' fun='edit' remarkId='"+data.map.activityRemark.id+"' href='javascript:void(0);'><span class='glyphicon glyphicon-edit' style='font-size: 20px; color: #E6E6E6;'></span></a>";
                    html += " &nbsp;&nbsp;&nbsp;&nbsp;";
                    html += "<a class='myHref' fun='del' remarkId='"+data.map.activityRemark.id+"' href='javascript:void(0);'><span class='glyphicon glyphicon-remove' style='font-size: 20px; color: #E6E6E6;'></span></a>";
                    html += "       </div>\n" +
                        "                </div>\n" +
                        "            </div>";

                   $("#remarkDiv").before(html);
                   $("#remark").val("");
               }else{
                   alert("系统忙,请稍后");
               }
           }
       })
    })
}

function view() {
    $("#remarkBody").on("mouseover",".remarkDiv",function(){
        $(this).children("div").children("div").show();

    });

    $("#remarkBody").on("mouseout",".remarkDiv",function () {
        $(this).children("div").children("div").hide();

    })

    $("#remarkBody").on("mouseover",".myHref",function () {
        $(this).children("span").css("color","red");
    });

    $("#remarkBody").on("mouseout",".myHref",function () {
        $(this).children("span").css("color","#E6E6E6");
    });


}

function deleteEvent() {
    var num = 0;
    $("div").on("click",".myHref",function () {
        if (num >0 ){
            return;
        }
        var fun = $(this).attr("fun");
        var id = $(this).attr("remarkId");
        if (fun == "del"){
        //    发起异步删除请求
            $.ajax({
                url:"/workbench/activity/deleteActivityRemark",
                data: {id,id},
                type: "delete",
                dataType: "json",
                success:function (data) {
                    if (data.responseStatus != "200"){
                        alert("系统忙,请稍候");
                        return;
                    }
                    $("#div_"+id).remove();
                }
            });
            ++num;
            return;
        }
        // // 发起异步修改请求
        // var content = $("#div_"+id).text();
        // $.ajax({
        //     url:"",
        //     data:{
        //         id:id,
        //         noteContent:content
        //     },
        //     type:"put",
        //     dataType:"json",
        //     success:function (data) {
        //         if (data.responseStatus == "200"){
        //
        //         }else{
        //             alert("系统忙,请稍候");
        //         }
        //     }
        // })

    })

}

function modifyEvent() {
    var num = 0;
    $("div").on("click",".myHref",function (){
        var fun = $(this).attr("fun");
        var id = $(this).attr("remarkId");
        var content = $.trim($("#div_"+id+" h5").text());
        if (fun == "edit"){
            //打开修改的模态窗口
            $("#updateRemarkBtn").attr("remarkId",id);
            $("#noteContent").val(content);
            $("#editRemarkModal").modal("show");
        }
    });
}

modifyRemark = ()=>{

    $("#updateRemarkBtn").click(function () {
        var id = $(this).attr("remarkId");
        var content = $.trim($("#noteContent").val());
        if (content == ""){
            alert("修改内容不允许为空");
            return;
        }
        $.ajax({
            url:"/workbench/activity/modifyActivityRemark",
            data: {
                id:id,
                noteContent:content
            },
            type: "put",
            dataType: "json",
            success:function (data) {
                if (data.responseStatus != "200"){
                    alert("系统忙,请稍候");
                    return;
                }else{
                    $("#div_"+id+" h5").text(data.map.activityRemark.noteContent);
                    $("small_"+id).text(data.map.activityRemark.editTime+" 修改");
                    $("#editRemarkModal").modal("hide");
                }
            }
        });

    })
}