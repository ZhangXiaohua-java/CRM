$(function () {

    bindingFocusEvent("#username");
    bindingFocusEvent("#pwd");
    $(window).keydown((event)=>{
        if (event.keyCode == 13){
            $("#loginBtn").click();
        }
    })
    $("#loginBtn").on("click",function () {
        $("#username").get(0).focus();
        $("#username").get(0).blur();
        $("#pwd").get(0).focus();
        $("#pwd").get(0).blur();
        var userTip = $("#usernameTip").text();
        userTip += $("#pwdTip").text();
        if (!userTip){
            $.ajax({
                url:"/settings/qx/user/login",
                type:"POST",
                data:{
                    "loginAct":$("#username").val(),
                    "loginPwd":$("#pwd").val(),
                    "isRemeber":$("#isRemeber").prop("checked")
                },
                beforeSend:()=>{$("#msg").text("后台正在校验中...")},
                success:function (data) {
                   if (data.responseStatus == "200"){
                       window.location.href = "/workbench/toIndex";
                   }else{
                       $("#msg").text(data.map.msg);
                   }
                }
            })
        }

    })



})

function bindingFocusEvent(element) {
    $(element).on("blur",function () {
        var value = $.trim($(this).val());
        if (value == ""){
           if ( $(this).prop("type") == "text"){
               $("#usernameTip").text("用户名不能为空");
           }
            if ( $(this).prop("type") == "password"){
                $("#pwdTip").text("密码不能为空");
            }
            return;
        }
        $("#usernameTip").text("");
        $("#pwdTip").text("");

    })
}