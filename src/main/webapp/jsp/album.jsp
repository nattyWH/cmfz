<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<script type="text/javascript">
    $(function () {
        $("#ftable").jqGrid(
            {
                url : '${app}/album/queryPage',
                datatype : "json",
                height : 388,
                editurl:"${app}/album/al",
                colNames : [ 'ID', '封面', '标题', '作者', '播音', '评分', '章节', '描述', '创建时间', '状态'],
                colModel : [
                    {name : 'id'},
                    {name : 'status',editable: true,
                            edittype: "file",
                            editoptions:{enctype:"multipart/form-data"},
                            formatter:function (data) {
                                return "<img src='"+data+"' style='width:100px;height:60px'>";
                        }
                    },
                    {name : 'title',editable: true},
                    {name : 'author',editable: true},
                    {name : 'broadcast',editable: true},
                    {name : 'score',editable: true},
                    {name : 'count',editable: true},
                    {name : 'descoption',editable: true},
                    {name : 'createDate',editable: true,editrules:{required:true},edittype: "date"},
                    {name : 'cover',editable: true,
                        edittype:"select",
                        editoptions: {value:"1:正常;2:冻结"},
                        formatter:function (data) {
                            if (data=="1"){
                                return "正常";
                            } else {
                                return "冻结";
                            }
                        }
                    },
                ],
                rowNum : 5,
                rowList : [ 5, 10, 15 ],
                pager : '#fpage',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                mtype:"get",
                multiselect : true,
                autowidth:true,
                styleUI:"Bootstrap",
                // 开启子表格支持
                subGrid : true,
                caption : "专辑信息",
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded : function(subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id,row_id);
                },
                // 删除表格的方法
                subGridRowColapsed : function(subgrid_id, row_id) {
                }
            });
        $("#ftable").jqGrid('navGrid', '#fpage', { edit:true,add:true,del:true},
            {
                closeAfterEdit: true,
                afterSubmit:function (response, postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${app}/album/uploadAlbum",
                        type:"post",
                        datatype:"json",
                        //发送添加图片的id到controller
                        data:{albumId:albumId},
                        //指定上传的input框id
                        fileElementId:"status",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterAdd:true,
                afterSubmit:function (response, postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${app}/album/uploadAlbum",
                        type:"post",
                        datatype:"json",
                        //发送添加图片的id到controller
                        data:{albumId:albumId},
                        //指定上传的input框id
                        fileElementId:"status",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },{
                closeAfterDel:true,
            });
    });
    // subgrid_id 父行级id
    function addSubgrid(subgrid_id,row_id) {
        // 声明子表格Id
        var sid = subgrid_id + "table";
        // 声明子表格工具栏id
        var spage = subgrid_id + "page";
        $("#"+subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='"+ spage +"' style='height: 50px'></div>")
        $("#" + sid).jqGrid(
            {
                // 指定的json文件
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url : "${app}/chapter/queryPage?albumId="+row_id,
                datatype : "json",
                editurl:"${app}/chapter/ch?albumId="+row_id,
                colNames : [ 'id', '标题','大小','时长','创建时间', '操作'],
                colModel : [
                    {name : "id"},
                    {name : "title"},
                    {name : "size"},
                    {name : "time"},
                    {name : "createTime",editable: true,editrules:{required:true},edittype: "date"},
                    {name : "url",editable: true,
                        edittype: "file",
                        editoptions:{enctype:"multipart/form-data"},
                        formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>&nbsp;&nbsp;";

                            button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"onPlay('"+cellvalue+"')\">在线播放</button>";
                            return button;
                        }
                    }
                ],
                rowNum : 5,
                pager : spage,
                sortname : 'num',
                sortorder : "asc",
                height : '100%',
                autowidth: true,
                styleUI:"Bootstrap",
                caption : "章节信息",
            });
        $("#" + sid).jqGrid('navGrid',
            "#" + spage,
            {
                edit : true,
                add : true,
                del : true
            },
            {
                closeAfterEdit:true,
                afterSubmit:function (response, postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${app}/chapter/uploadChapter",
                        type:"post",
                        datatype:"json",
                        //发送添加图片的id到controller
                        data:{chapterId:chapterId},
                        //指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#" + sid).trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterAdd:true,
                afterSubmit:function (response, postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url:"${app}/chapter/uploadChapter",
                        type:"post",
                        datatype:"json",
                        //发送添加图片的id到controller
                        data:{chapterId:chapterId},
                        //指定上传的input框id
                        fileElementId:"url",
                        success:function (data) {
                            $("#ftable").trigger("reloadGrid");
                        }
                    });
                    return postData;
                }
            },
            {
                closeAfterDel:true
            })
    };
    <%-- 在线播放 --%>
    function onPlay(cellValue) {
        $("#music").attr("src",cellValue);
        $("#myModal").modal("show");
    }
    <%-- 下载 --%>
    function download(cellValue) {
        location.href = "${app}/chapter/downUpload?url="+cellValue;
    }
</script>
<body>
<table id="ftable"></table>
<div id="fpage" style="height: 50px"></div>
</body>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>