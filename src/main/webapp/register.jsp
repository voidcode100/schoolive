<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>注册</h1>
        <form id="registerForm" action="register" method="post">
            <label for="username">用户名：</label>
            <input type="text" id="username" name="username" placeholder="请输入用户名" required>
            
            <label for="password">密码：</label>
            <input type="password" id="password" name="password" placeholder="请输入密码" required>
            
            <label for="email">邮箱：</label>
            <input type="email" id="email" name="email" placeholder="请输入邮箱" required>
            
            <button type="submit">注册</button>
        </form>
        <p>已有账号？<a href="login.jsp">立即登录</a></p>
    </div>
    <script src="js/scripts.js"></script>
</body>
</html>