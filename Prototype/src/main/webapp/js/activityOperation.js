var  pageNo =1;
var pageSize = 10;
var totalPages = 1;
var  visiblePages = 9;
var rsc_bs_pag = {
    go_to_page_title: "Go to page",
    rows_per_page_title: "Rows per page",
    current_page_label: "Page",
    current_page_abbr_label: "p.",
    total_pages_label: "of",
    total_pages_abbr_label: "/",
    total_rows_label: "of",
    rows_info_records: "records",
    go_top_text: "&laquo;",
    go_prev_text: "&larr;",
    go_next_text: "&rarr;",
    go_last_text: "&raquo;"
};
$(function () {
    calendarLoad();
    forwardAjaxQueryActivity();
    processPages();
    queryActivityBtnEvent();
    deleteEvent();
    checkAll();
    editEvent();
    updateActivityInfo();
    exportActivity();
    importActivity();
    selectiveExportInfo();
})

//全选与取消全选
var checkAll = ()=>{

    $("#checkAll").click(function () {
        $("#activityInfoBody input[type='checkbox']").prop("checked",$(this).prop("checked"));
    });

    $("#activityInfoBody").on("click","input[type='checkbox']",()=>{
        var checkBox = $("#activityInfoBody input[type='checkbox']");
        $("#checkAll").prop("checked",
            checkBox.size() == $("#activityInfoBody input[type='checkbox']:checked").size());
    });


}


//删除活动按钮事件
function deleteEvent() {

    $("#deleteActivityBtn").click(function (index, element) {
        var comment = "" ;
        var id = new Array();
        $.each($("#activityInfoBody input[type='checkbox']:checked"),function (index,element) {
           id[index] = element.value;
        });
        if (id.length == 0){
            alert("请选择要删除的信息");
            return;
        }
        if (confirm("是否确认删除?")){
            var  ids = "";
            for (let i = 0; i <id.length ; i++) {
                ids += id[i]+"_";
            }
        //    发起异步ajax请求删除信息
        $.ajax({
            url:"/workbench/activity/deleteActivity",
            type:"DELETE",
            dataType: "json",
            data:{"ids":ids},
            success:(data)=>{
                if (data.responseStatus == 200){
                    pageNo = 1;
                    forwardAjaxQueryActivity();
                }else{
                    alert(data.message);
                }
            }
        })


        }

    }
    )
}

//处理分页
function processPages() {
        $("#pages").bs_pagination({
            currentPage:pageNo,
            rowsPerPage:pageSize,
            totalPages:totalPages,
            visiblePageLinks: visiblePages,
            onChangePage:(event,pageObj)=>{
                pageNo = pageObj.currentPage;
                pageSize = pageObj.rowsPerPage;
                forwardAjaxQueryActivity();
            }

        })

}


//处理页面中日期输入框的日历插件
var  calendarLoad = ()=>{
    $(".calendar").datetimepicker({
        language:"zh-CN",
        format:"yyyy-mm-dd",
        weekStart:1,
        minView:2,
        todayBtn:true,
        autoclose:true,
        todayHighlight:1,
        forceParse:true,
        clearBtn:true,
        initialDate:new Date()
    });
    $(".calendar").prop("readonly","true");
}


//处理分页查询的函数,默认显示第一页的数据,pageSize默认为10
var forwardAjaxQueryActivity = ()=>{
        $.ajax({
            url:"/workbench/activity/queryActivities",
            data: getCondition(),//获取查询条件
            type:"Get",
            success:function (data) {
                //渲染页面
                if (data.responseStatus == 200){
                    // totalRows
                    $("#checkAll").prop("checked",false);
                    var counts = data.map.totalRows;
                    totalPages = counts % pageSize == 0 ? counts / pageSize : Math.ceil((counts / pageSize)) ;
                    if (totalPages >= visiblePages){
                        visiblePages = 10;
                    }else{
                        visiblePages = totalPages;
                    }
                    processPages();
                    renderPage(data.map.activitiesList);
                }else{
                    alert("服务异常,稍后再试");
                }

            }
        })
    }


//    获取查询条件

    var getCondition = ()=>{
        // var activityName = $("#activityName").val();
        // var ownerName = $("#ownerName").val();
        var condition = $("#queryCondition").serialize();
        condition += "&pageNo="+pageNo;
        condition += "&pageSize="+pageSize;
        return condition;
    }



//    渲染页面
var renderPage = (data)=>{
        var html = "";
        $.each(data,function (index,element) {
            var ele = "<tr><td><input type='checkbox' value='"+element.id+"'/></td>";
            ele += "<td><a style=\"text-decoration: none; cursor: pointer;\" href='"+"/workbench/activity/queryActivityInfo/"+element.id+"'>"+element.name+"</a></td>";
            // ele += "<td><a style=\"text-decoration: none; cursor: pointer;\" href='"+"detail.jsp?id="+id+"' onClick=\"window.location.href='detail.jsp';\">"+element.name+"</a></td>";
            ele += "<td>"+element.owner+"</td>";
            ele += "<td>"+element.startDate+"</td>";
            ele += "<td>"+element.endDate+"</td></tr>";
            html += ele;
        })
        //更新页面数据
        $("#activityInfoBody").html(html);

    }




//根据条件查询满足条件的活动
    var queryActivityBtnEvent = ()=>{
        $("#queryActivityBtn").on("click",function () {
            pageNo = 1;
            forwardAjaxQueryActivity();
        })
    }








//    将index.jsp页面中内嵌的js代码块移植过来
var timeFlag = false;
var regexCost = /^\d+(\.?)\d+$/gi;
var globalFlag = false;


$(function(){

    $("#createActivityBtn").on("click",()=>{
        $("#createActivityForm").get(0).reset();
        //打开创建市场活动的模态框
        $("#createActivityModal").modal("show");

    });
    bindBlurEvent();
    forwardSaveActivity();



});

//	市场活动名称的校验
function bindBlurEvent(){
    checkName();
    var startTime = $("#create-startTime");
    var endTime = $("#create-endTime");
    startTime.blur(function () {
        checkTime();
    });
    endTime.blur(function () {
        checkTime();
    });
    checkCost();
}



function checkName() {
    //市场活动名称blur事件
    $("#create-marketActivityName").blur(function (){
        if ($(this).val() == ""){
            $("#createActivityNameTip").text("市场活动名不能为空");
        }else{
            $("#createActivityNameTip").text("");
        }
    });
}



//	活动日期的校验,只要开始时间与结束时间都不为空,就必须满足结束时间大于开始时间
function checkTime(){
    var startTime = $.trim($("#create-startTime").val());
    var endTime = $.trim($("#create-endTime").val());
    if (startTime != "" && endTime != ""){
        if (startTime < endTime){
            timeFlag = true;
            $("#createActivityTimeTip").text("");
        }else{
            timeFlag = false;
            $("#createActivityTimeTip").text("结束时间必须晚于开始时间");
        }
    }
}



function checkCost() {
    $("#create-cost").blur(function () {
        // var flag = regexCost.test($.trim($(this).val()));
        // if (!flag){
        // 	$("#tip").text("成本不能是非数字");
        // }else{
        // 	$("#tip").text("");
        // }
        var cost = $.trim($(this).val());
        if (cost == ""){
            $("#costTip").text("成本不能为空");
            return;
        }
        if (isNaN(cost)){
            $("#costTip").text("成本不能是非数字");
            return;
        }else{
            cost = Number.parseFloat(cost);
            if (cost < 0){
                $("#costTip").text("成本不能是负数");
                return;
            }else{
                $("#costTip").text("");
            }
        }

    })

}




var forwardSaveActivity = ()=>{
    $("#saveActivityBtn").click(function () {
        lastCheck();
        if (!globalFlag){
            //不允许提交
            alert("请检查参数");
            return;
        }
        //可以提交
        var owner = $("#create-marketActivityOwner option:selected").val();
        var  data = $("#createActivityForm").serialize()+"&owner="+owner;
        $.ajax({
            url:"/workbench/activity/addActivity",
            data:data,
            type:"POST",
            dataType:"json",
            success:(data)=>{
                console.log(data)
                if (data.responseStatus == 200){
                    $("#createActivityModal").modal("hide");
                    forwardAjaxQueryActivity();
                }else{
                    alert(data.map.msg);
                }
            }
        })


    })
}




var  lastCheck = ()=>{
    // $("#create-startTime"),$("#create-endTime"), 暂时删除
    var objArray = [$("#create-marketActivityName"),$("#create-cost")];
    var tipArray = [$("#costTip"),$("#createActivityNameTip"),$("#createActivityTimeTip")];
    var comment = "";
    $.each(objArray,function (index, element) {
        element.focus();
        element.blur();
    });
    $.each(tipArray,function (index, element) {
        comment += element.text();
    })
    if (comment == ""){
        globalFlag = true;
        return;
    }else{
        globalFlag = false;
    }


}

//为修改按钮绑定事件
var editEvent = ()=>{

    $("#editActivityBtn").click(()=>{
        var checkedActivities = $("#activityInfoBody input[type='checkbox']:checked");
        if (checkedActivities.size() == 0){
            alert("请选择要修改的记录");
            return;
        }
        if (checkedActivities.size() >1){
            alert("只能同时修改一条记录");
            return;
        }
        var id = checkedActivities.val();
        $("#editActivityForm").get(0).reset();
        forwardAjaxQueryActivityInfo(id);
        $("#editActivityModal").modal("show");
    });
}

// 查询要修改的市场活动的信息

forwardAjaxQueryActivityInfo = (id)=>{
    $.ajax({
        url:"/workbench/activity/queryActivity/"+id,
        dataType:"json",
        success:(data)=>{
            if (data.responseStatus == 200){
                renderEditForm(data.map.activity);
            }
        }
    })

}

//渲染修改市场活动的表单
renderEditForm = (data)=>{
    console.log(data);
    $("#updateBtn").attr("edit_id",data.id);
    $("#editActivityName").val(data.name);
    $("#editStartTime").val(data.startDate);
    $("#editEndTime").val(data.endDate);
    $("#editCost").val(data.cost);
    $("#edit-describe").val(data.description);
    $("#edit-marketActivityOwner").val(data.owner);
}

//修改市场活动信息

updateActivityInfo = ()=>{
    $("#updateBtn").click(()=>{
        var id = $("#updateBtn").attr("edit_id");
        var  name = $("#editActivityName").val();
        var startDate = $("#editStartTime").val();
        var endDate = $("#editEndTime").val();
        var cost = $("#editCost").val();
        var description = $("#edit-describe").val();
        var owner = $("#edit-marketActivityOwner").val();
        $.ajax({
            url:"/workbench/activity/updateActivityInfo",
            data:{
                id:id,
                name:name,
                startDate:startDate,
                endDate:endDate,
                owner:owner,
                description:description,
                cost:cost
            },
            dataType:"json",
            type:"PUT",
            success:(data)=>{
                if (data.responseStatus == 200){
                    $("#editActivityModal").modal("hide");
                    forwardAjaxQueryActivity();
                    return;
                }
                alert(data.message);
            }
        })
    })
}


exportActivity = ()=>{
    $("#exportActivityAllBtn").click(()=>{
        window.location.href = "/workbench/activity/exportAllActivities";
    })
}

importActivity = ()=>{
    $("#importActivityBtn").click(function () {
        var activityFileDom = $("#activityFile").get(0);
        var fileName = activityFileDom.value;
        let fileNameSuffix = fileName.substring(fileName.lastIndexOf(".")+1).toLocaleUpperCase();
        console.log(fileNameSuffix);
        if (fileNameSuffix != "XLS" && fileNameSuffix !="XLSX"){
            alert("只支持Excel格式的文件");
            return;
        }
        var file = activityFileDom.files[0];
        console.log(file.size);
        if (file.size>1024*1024*5){
            alert("上传文件大小不允许超过5MB");
            return;
        }
        var formData = new FormData();
        formData.append("activityFile",file);
        $.ajax({
            url:"/workbench/activity/importActivity",
            processData:false,
            contentType:false,
            dataType:"json",
            type:"post",
            data:formData,
            success:function (data) {
                if (data.responseStatus == "200"){
                    alert("成功导入"+data.map.rowCount+"条记录");
                    $("#importActivityModal").modal("hide");
                    forwardAjaxQueryActivity();
                }else{
                    alert("系统忙,请稍候...");
                }

            }

        })


    })

}

//下载Excel模板文件

downloadTemplateFile = ()=>{
        window.location.href = "/workbench/activity/templateDownload";
        window.event.returnValue = false;
}


//选择导出信息
function selectiveExportInfo() {
    $("#exportActivityXzBtn").click(function () {
        var checkedList = $("#activityInfoBody input[type='checkbox']:checked");
        if (checkedList.size() ==0){
            alert("请选中要导出的市场活动信息");
            return;
        }
        var array =new Array(checkedList.size());
        var parameter = "";
        $.each(checkedList,function (index,element) {
           parameter += "ids="+element.value+"&";
        });
        window.location.href = "/workbench/activity/exportActivityInfoSelective?"+parameter;
    })

}
