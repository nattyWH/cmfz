<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#list2").jqGrid(
            {
                url : '${app}/guru/queryPageGuru',
                datatype : "json",
                styleUI: 'Bootstrap',
                editurl:"${app}/guru/gu",
                colNames : [ 'ID', '名字', '图片', '状态','法号'],
                colModel : [
                    {name : 'id'},
                    {name : 'name',editable: true},
                    {name : 'photo',editable: true,
                                edittype: "file",
                                editoptions:{enctype:"multipart/form-data"},
                                formatter:function (cellvalue, options, rowObject) {
                                    return "<img src='"+cellvalue+"' style='width:100px;height:60px'>";
                                }
                    },
                    {
                        name : 'status',
                        formatter:function (data) {
                            if(data=="1"){
                                return "正常";
                            }else{
                                return "冻结";
                            }
                        },editable: true,
                        editrules:{required:true},
                        edittype: "select",
                        editoptions: {value:"1:正常;2:冻结"}
                    },
                    {name : 'nickName',editable: true},
                ],
                rowNum : 3,
                rowList : [ 3, 6, 9 ],
                pager : '#guruPager',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "上师信息",
                height: "280px",
                autowidth: true,
                multiselect: true,
            });
            $("#list2").jqGrid('navGrid', '#guruPager', {edit:true,add:true,del:true},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response, postData) {
                        var guruId = response.responseJSON.guruId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${app}/guru/uploadGuru",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id到controller
                            data:{guruId:guruId},
                            //指定上传的input框id
                            fileElementId:"photo",
                            success:function (data) {
                                $("#list2").trigger("reloadGrid");
                            }
                        });
                        return postData;
                    }
                },
                {
                    closeAfterAdd:true,
                    afterSubmit:function (response, postData) {
                        var guruId = response.responseJSON.guruId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${app}/guru/uploadGuru",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id到controller
                            data:{guruId:guruId},
                            //指定上传的input框id
                            fileElementId:"photo",
                            success:function (data) {
                                $("#list2").trigger("reloadGrid");
                            }
                        });
                        return postData;
                    }
         });
    });
</script>

<div class="page-header">
    <h4>上师信息</h4>
</div>
<table id="list2"></table>
<div id="guruPager" style="height: 100px"></div>