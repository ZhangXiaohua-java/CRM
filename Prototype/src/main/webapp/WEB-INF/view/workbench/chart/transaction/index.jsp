<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String basePath = request.getScheme()+"://"+request.getServerName()+":"
        +request.getServerPort()+":"+request.getContextPath();
%>
<html>
<head>
    <base href="${basePath}">
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="/jquery/echars/echarts.min.js"></script>
    <script type="text/javascript">
        var data = [];
        $(function () {
            var chart  = echarts.init(document.getElementById("chart"));
            $.ajax({
                url:"/workbench/chart/showChart",
                type:"get",
                async:false,
                dataType:"json",
                success:function (dat) {
                    data = dat;
                }
            });

            var  option = {
                title: {
                    text: 'Funnel'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c}%'
                },
                toolbox: {
                    feature: {
                        dataView: { readOnly: false },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                legend: {
                    data: ['Show', 'Click', 'Visit', 'Inquiry', 'Order']
                },
                series: [
                    {
                        name: 'Funnel',
                        type: 'funnel',
                        left: '10%',
                        top: 60,
                        bottom: 60,
                        width: '80%',
                        min: 0,
                        max: 100,
                        minSize: '0%',
                        maxSize: '100%',
                        sort: 'descending',
                        gap: 2,
                        label: {
                            show: true,
                            position: 'inside'
                        },
                        labelLine: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        },
                        itemStyle: {
                            borderColor: '#fff',
                            borderWidth: 1
                        },
                        emphasis: {
                            label: {
                                fontSize: 20
                            }
                        },
                        data:data
                    }
                ]
            };
            chart.setOption(option);
        })
    </script>
</head>
<body>


<div id="chart" style="width: 600px;height: 400px">


</div>
</body>
</html>
