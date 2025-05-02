<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>登录</h1>
        <form id="loginForm" action="login" method="post">
            <label for="username">用户名：</label>
            <input type="text" id="username" name="username" placeholder="请输入用户名" required>
            
            <label for="password">密码：</label>
            <input type="password" id="password" name="password" placeholder="请输入密码" required>
            
            <button type="submit">登录</button>
        </form>
        <p>还没有账号？<a href="register.jsp">立即注册</a></p>
    </div>
    <script src="js/scripts.js"></script>
</body>
</html>