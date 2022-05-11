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
var pageSize = 10 ;
var visiblePages =9;
var totalPages = 1 ;
$(function () {
    queryCount();


})

function page() {
    $("#pageBody").bs_pagination({
            currentPage:pageNo,
            rowsPerPage:pageSize,
            totalPages:totalPages,
            visiblePageLinks: visiblePages,
            onChangePage:(event,pageObj)=>{
                pageNo = pageObj.currentPage;
                pageSize = pageObj.rowsPerPage;
                    queryForPage();
                }

    });

}

function queryCount() {
    $.ajax({
        url:"/workbench/customer/queryCount",
        type:"get",
        async:false,
        dataType:"json",
        success:function (data) {
            console.log(data);
            var rowCunt = Number.parseInt(data);
            totalPages = rowCunt % pageSize ==0 ? rowCunt/pageSize:Math.ceil(rowCunt/pageSize);
            if (totalPages  > 10 ){
                visiblePages = 10;
            }else{
                visiblePages =totalPages;
            }
            console.log(totalPages);
            console.log(visiblePages);
            page();
        }
    })

}

//分页查询
function queryForPage() {

    $.ajax({
        url:"/workbench/customer/customer/"+pageNo+"/"+pageSize,
        dataType:"json",
        success:function (data) {
            if (data.responseStatus == "200"){
                var html = "";
                $.each(data.map.customers,function (index,element) {
              html +=  "<tr>"
              html +=  "<td><input type='checkbox' value='"+element.id+"'/></td>"
              html +=  "<td><a style='text-decoration: none; cursor: pointer;' href='/workbench/customer/customerDetail/"+element.id+"'>"+element.name+"</a></td>"
              html +=  " <td>"+element.owner+"</td>";
              html +=  " <td>"+element.phone+"</td>";
              html +=  " <td>"+element.website+"</td>";
              html +=  " </tr>";
                });
                $("#customerBody").html(html);
                queryCount();
                console.log(data)
            }
        }
    })

}