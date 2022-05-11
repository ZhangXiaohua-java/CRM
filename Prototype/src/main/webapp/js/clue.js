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
var pageNo = 1;
var pageSize = 10;
var visiblePages = 9;
var totalPages  = 1;
$(function () {
    queryCount();
    createModalShow();
    forwardCreateClue();

})


//创建线索

function createModalShow() {
    $("#createClueBtn").click(function () {
        $("#createClueModal").modal("show");
    })
}

//发起创建线索的异步请求
function forwardCreateClue() {
    $("#saveClueBtn").click(function () {
        //校验通过后发起请求,暂时偷懒不想做表单校验,如果可以把校验放在后端,前端的校验走个形式,写吐了
        //异步请求
        $.ajax({
                url:"/workbench/clue/saveClue",
                type:"post",
                data:$("#createClueForm").serialize(),
                dataType:"json",
                success:function (data) {
                    if (data.responseStatus == "200"){
                        $("#createClueModal").modal("hide");
                    //    调用分页查询的函数,暂时偷懒直接更改地址栏地址再向后台controller发起一次请求
                        window.location.href = "/workbench/clue/toClueIndex";
                        return;
                    }
                    alert("系统忙,请稍候");
                }
        })
    })

}

//分页插件
function pagePlugin() {
    $("#pageBody").bs_pagination({
        currentPage:pageNo,
        rowsPerPage:pageSize,
        totalPages:totalPages,
        visiblePageLinks: visiblePages,
        onChangePage:(event,pageObj)=>{
            pageNo = pageObj.currentPage;
            pageSize = pageObj.rowsPerPage;
            forwardPageQuery();
        }

    })

}

//史上最懒的分页查询,实在不想拼接页面了

forwardPageQuery=function(){
    $.ajax({
        url: "/workbench/clue/pageQueryClue",
        data: {
            pageNo:pageNo,
            pageSize:pageSize
        },
        dataType: "json",
        success:function (data) {
            var html = "";
            $.each(data.map.clueList,function (index,element) {
                html += "<tr>";
                html += "<td><input type='checkbox' value='"+element.id+"' /></td>"
                html += "<td><a style= 'text-decoration: none; cursor: pointer;' onclick='window.location.href=/workbench/clue/queryClueDetailInfo/'"+element.id+"'>"+element.fullname+"</a></td>"
                html += "<td>"+element.company+"</td>";
                html += "<td>"+element.phone+"</td>";
                html += "<td>"+element.mphone+"</td>";
                html += "<td>"+element.source+"</td>";
                html += "<td>"+element.owner+"</td>";
                html += "<td>"+element.state+"</td>";
                html += "</tr>";
            });
            queryCount();
            $("#clueBody").html("");
            $("#clueBody").html(html);
        }
    })

}

queryCount = function () {
    $.ajax({
        url:"/workbench/clue/queryCount",
        dataType:"json",
        async:false,
        success:function (data) {
            var rowCount = Number.parseInt(data.map.recordsCount);
            totalPages =  rowCount % pageSize ==0 ? rowCount/pageSize:Math.ceil(rowCount /pageSize);
            console.log(totalPages)
            if (totalPages >10){
                visiblePages = 10;
            }else{
                visiblePages =totalPages;
            }
            pagePlugin();
        }
    })

}

