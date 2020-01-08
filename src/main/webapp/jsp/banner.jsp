<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#list2").jqGrid(
            {
                url : '${app}/banner/queryPage',
                datatype : "json",
                styleUI: 'Bootstrap',
                editurl:"${app}/banner/ba",
                colNames : [ 'ID', '标题', '路径', '创建日期','描述', '状态'],
                colModel : [
                    {name : 'id'},
                    {name : 'title',editable: true},
                    {name : 'url',editable: true,
                                edittype: "file",
                                editoptions:{enctype:"multipart/form-data"},
                                formatter:function (cellvalue, options, rowObject) {
                                    return "<img src='"+cellvalue+"' style='width:100px;height:60px'>";
                                }
                    },
                    {name : 'createDate',editable: true},
                    {name : 'descoption',editable: true},
                    {
                        name : 'status',
                        formatter:function (data) {
                            if(data=="1"){
                                return "正常";
                            }else{
                                return "冻结";
                            }
                        },editable: true,
                        editrulles:{required:true},
                        edittype: "select",
                        editoptions: {value:"1:正常;2:冻结"}
                    }
                ],
                rowNum : 3,
                rowList : [ 3, 6, 9 ],
                pager : '#pager2',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "轮播图信息",
                height: "280px",
                autowidth: true,
            });
            $("#list2").jqGrid('navGrid', '#pager2', {edit:true,add:true,del:true},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response, postData) {
                        var bannerId = response.responseJSON.bannerId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${app}/banner/uploadBanner",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id到controller
                            data:{bannerId:bannerId},
                            //指定上传的input框id
                            fileElementId:"url",
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
                        var bannerId = response.responseJSON.bannerId;
                        $.ajaxFileUpload({
                            //指定上传路径
                            url:"${app}/banner/uploadBanner",
                            type:"post",
                            datatype:"json",
                            //发送添加图片的id到controller
                            data:{bannerId:bannerId},
                            //指定上传的input框id
                            fileElementId:"url",
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
    <h4>轮播图信息</h4>
</div>
<table id="list2"></table>
<div id="pager2" style="height: 100px"></div>