<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="${app}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <script>
        function chgCode(){
            document.getElementById("img1").src = "${app}/admin/createImg?xx="+Math.random();
        }

        function login() {
            $.post(
                "${app}/admin/login",
                $("#loginForm").serialize(),

                function (re) {
                    if(re=="ok"){
                        location.href="${app}/jsp/main.jsp";
                    }
                    if(re=="codeError"){
                        document.getElementById("msg").innerHTML="<a style='color: red'>验证码错误</a> ";
                    }
                    if(re=="error"){
                        document.getElementById("msg").innerHTML="<a style='color: red'>账号或密码错误</a> ";
                    }
                },
        "json")};
    </script>
</head>
<body style=" background: url(${app}/img/timg.jpeg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <img id="img1" src="${app}/admin/createImg"/>
                <a href="javascript:void(0)" onclick="chgCode()">看不清</a>
                <input type="text" class="form-control" placeholder="验证码" autocomplete="off" name="code"/>
            </div>
            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
