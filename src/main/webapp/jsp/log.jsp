<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#list2").jqGrid(
            {
                url : '${app}/log/queryPage',
                datatype : "json",
                styleUI: 'Bootstrap',
                colNames : [ 'ID', '操作', '名字', '日期', '状态(成功、失败)'],
                colModel : [
                    {name : 'id'},
                    {name : 'thing'},
                    {name : 'name'},
                    {name : 'date'},
                    {name : 'result'}
                ],
                rowNum : 7,
                rowList : [ 7, 14, 21 ],
                pager : '#pager2',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "日志信息",
                height: "280px",
                autowidth: true,
            })
    });
</script>

<div class="page-header">
    <h4>日志信息</h4>
</div>
<table id="list2"></table>
<div id="pager2" style="height: 100px"></div>