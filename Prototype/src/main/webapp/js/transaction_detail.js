$(function () {
    possibility();
    queryActivitySource();
    createTransaction();
    typeHeadPlugin();
    searchContacts();
})

//可能性配置
function possibility() {
    $("#create-transactionStage").change(function () {
        var  value = this.value;
        // var text = $(this).find("option:selected").text();
        if (!value){
            $("#create-possibility").val("");
            return;
        }
        $.ajax({
            url:"/workbench/transaction/possibilityAnalysis",
            data:{keyWord:value},
            dataType:"json",
            type:"get",
            success:function (data){
                $("#create-possibility").val(data+"%");
            }

        })

        //修改可能性的值

    })

}

//查询活动源
function queryActivitySource() {

    $("#searchActivityComonent").click(function () {
        $("#findMarketActivity").modal("show");
    });
    $("#searchCondition").keyup(function () {
        var activityName = this.value;
        if (!activityName){
            return ;
        }
        $.ajax({
            url:"/workbench/activity/fuzzyQueryActivity",
            data: {activityName:activityName},
            dataType: "json",
            type: "get",
            success:function (data) {
                var html = "";
                $.each(data,function (index,element) {
                    html += "<tr>";
                    html += "<td><input type='radio' activityName='"+element.name+"' value='"+element.id+"' name='activity'/></td>";
                    html += "<td>"+element.name+"</td>";
                    html += "<td>"+element.startDate+"</td>";
                    html += "<td>"+element.endDate+"</td>";
                    html += "<td>"+element.owner+"</td>";
                    html += "</tr>";
                });
                $("#activitySource").html(html);
            }
        })
        
    });
    
//    绑定单击事件
    $("#activitySource").on("click","input[type='radio']",function () {
        var activityId = $(this).val();
        var activityName = $(this).attr("activityName");
        $("#saveBtn").attr("activityId",activityId);
        $("#saveBtn").attr("activityName",activityName);
        console.log(activityId);
        console.log(activityName);
        $("#create-activitySrc").val(activityName);
        $("#findMarketActivity").modal("hide");
    });
    $("#contactsBody").on("click","input[type='radio']",function () {
        var contactsId = $(this).val();
        var contactsName = $(this).attr("contactsName");
        console.log(contactsId)
        console.log(contactsName);
        $("#saveBtn").attr("contactsId",contactsId);
        $("#create-contactsName").val(contactsName);
        $("#findContacts").modal("hide");

    })
}

//保存交易信息
function createTransaction() {
    $("#saveBtn").click(function () {
        var  activityId = $("#saveBtn").attr("activityId");
        var  contactsId = $("#saveBtn").attr("contactsId");
        var  activityName = $("#saveBtn").attr("activityName");
        var data = $("#transactionForm").serialize();
        data += "&activityId="+activityId+"&contactsId="+contactsId;
        console.log(data);
        //这里不想做表单验证了
        $.ajax({
            url:"/workbench/transaction/createTransaction",
            data:data,
            type:"post",
            dataType:"json",
            success:function (data) {
                if (!data.responseStatus == "200"){
                    alert("系统忙");
                }else {
                    window.location.href = "/workbench/transaction/toIndex";
                }
            }
        })
    })

}

//联系人自动补全

function typeHeadPlugin() {
    
    $("#create-accountName").typeahead({
        source:function (jquery,process) {
            //获取数据
            $.ajax({
                url:"/workbench/customer/fuzzyQueryCustomer",
                data:{name:jquery},
                type:"get",
                dataType:"json",
                success:function (data) {
                    process(data);
                }
            })
        }
    })

}

//搜索联系人信息
function searchContacts() {
    $("#searchContacts").click(function () {
        $("#findContacts").modal("show");
    });
    $("#searchContactsCondition").keyup(function () {
        var  name = this.value;
        $.ajax({
            url:"/workbench/contacts/queryContacts",
            data:{name:name},
            dataType:"json",
            type:"get",
            success:function (data) {
                console.log(data);
                if (data.responseStatus == "200"){
                    var html = "";
                    $.each(data.map.contactsList,function (index,element) {
                        html += "<tr>";
                        html += "<td><input type='radio' contactsName='"+element.fullname+"' value='"+element.id+"' name='activity'/></td>"
                        html += "<td>"+element.fullname+"</td>";
                        html += "<td>"+element.email+"</td>";
                        html += "<td>"+element.mphone+"</td>";
                        html += "</tr>";
                    })
                    $("#contactsBody").html(html);
                }
            }
        })
    })

}