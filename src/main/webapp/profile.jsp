<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.schoolive.beans.UserBean" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    UserBean user = (UserBean) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人主页 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <header>
        <h1>个人主页</h1>
        <nav>
            <a href="home.jsp">主页</a>
            <a href="logout">退出登录</a>
        </nav>
    </header>
    <main>
        <h2>欢迎，<%= user.getUsername() %>！</h2>
        <p>邮箱：<%= user.getEmail() %></p>
        <p>简介：<%= user.getBio() %></p>
        <section>
            <h2>我的帖子</h2>
            <div id="userPostList" class="post-list">
                <!-- 动态加载用户帖子 -->
            </div>
        </section>
    </main>
    <footer>
        <p>&copy; 2025 Schoolive. All rights reserved.</p>
    </footer>
    <script src="js/scripts.js"></script>
</body>
</html>