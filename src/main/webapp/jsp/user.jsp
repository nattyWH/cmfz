<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#userTable").jqGrid({
            url: "${app}/user/userPage",
            datatype: "json",
            colNames: ['id', '电话', '密码', '头像', '名字', '昵称','性别','地区','状态','注册时间','最后登陆时间'],
            colModel: [
                {name: 'id'},
                {name: 'phone',editable:true},
                {
                    name: 'password',editable:true
                },
                {name: 'photo',edittype:"file",editable:true,
                    editoptions: {enctype:"multipart/form-data"},formatter: function (cellvalue, options, rowObject) {
                        return "<img src='"+ cellvalue + "' style='width:100px;height:60px'>";
                    }
                },
                {
                    name: 'name',editable:true
                },
                {
                    name: 'nickName',editable:true

                },
                {name:'sex',editable:true,editrules:{required:true},edittype:"select",editoptions:{value:"1:男;2:女"},
                            formatter:function(data){
                                    if(data=="1"){
                                        return "男";
                                    }else{
                                        return "女";
                                    }
                            }
                },
                {name:'location',editable:true},
                {name:'status',editable:true,
                    editrules:{required:true},edittype:"select",editoptions:{value:"1:正常;2:冻结"},
                    formatter:function(data){
                        if(data=="1"){
                            return "正常";
                        }else{
                            return "冻结";
                        }
                    }
                },
                {name:'rigestDate'},
                {name:'lastLogin'}
            ],
            autowidth: true,
            pager: "#userPage",
            rowNum: 5,
            rowList: [5, 10, 15, 20],
            viewrecords: true,
            caption: "用户",
            styleUI: "Bootstrap",
            height: "330px",
            multiselect:true,
            editurl: "${app}/user/us"

        });
        jQuery("#userTable").jqGrid('navGrid', '#userPage',{edit: true, add: true, del: true},
            // 制定修改|添加|删除 之前 之后的事件
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${app}/user/uploadUser",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{userId:userId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (data) {
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },{
                closeAfterAdd: true,
                // 数据库添加轮播图后 进行上传 上传完成后需更改url路径 需要获取添加轮播图的Id
                //                   editurl 完成后 返回值信息
                afterSubmit:function (response,postData) {
                    var userId = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        // 指定上传路径
                        url:"${app}/user/uploadUser",
                        type:"post",
                        datatype:"json",
                        // 发送添加图片的id至controller
                        data:{userId:userId},
                        // 指定上传的input框id
                        fileElementId:"photo",
                        success:function (data) {
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    // 防止页面报错
                    return postData;
                }
            },{
                closeAfterDel: true,
            });
    });

    /*导出*/
    function poiExe(){
        $.ajax({
            url:"${app}/user/imageUpload",
            type:"post",
            datatype: "json",
            success:function (){
                $("#userTable").trigger("reloadGrid");
            }
        })
    }

    /*导入*/
    function lead(){
        $.ajax({
            url:"${app}/user/leadExcel",
            type:"post",
            datatype: "json",
            success:function (){
                $("#userTable").trigger("reloadGrid");
            }
        })
    }


</script>


<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
    <li><a onclick="poiExe()">用户数据导出</a></li>
    <li><a onclick="lead()">用户数据导入</a></li>
</ul>


<table id="userTable"></table>
<div id="userPage" style="height: 50px"></div>