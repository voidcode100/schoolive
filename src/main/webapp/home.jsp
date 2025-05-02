<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>主页 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <header>
        <h1>Schoolive</h1>
        <nav>
            <a href="profile.jsp">个人主页</a>
            <a href="logout">退出登录</a>
        </nav>
    </header>
    <main>
        <h2>帖子列表</h2>
        <!-- 发布帖子按钮 -->
        <div class="post-actions">
            <button onclick="location.href='createPost.jsp'" class="create-post-button">发布帖子</button>
        </div>
        <div id="postList" class="post-list">
            <!-- 动态加载帖子 -->
        </div>
    </main>
    <footer>
        <p>&copy; 2025 Schoolive. All rights reserved.</p>
    </footer>
    <script src="js/scripts.js"></script>
</body>
</html>