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
    <title>主页 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <header>
        <h1>欢迎，<%= user.getUsername() %>！</h1>
        <nav>
            <a href="profile.jsp">个人主页</a>
            <a href="logout">退出登录</a>
        </nav>
    </header>
    <main>
        <h2>校园圈子</h2>
        <p>这里是东华理工校园圈，在这里你可以分享在校园中遇到的有趣的人，事，物。</p>
        <p>也可以在这里发布一些校园活动的信息，或者是一些二手交易的信息。</p>
        <p>同时你可以在每一位同学发布的帖子下进行讨论、锐评，你大可以把这个网站视为一个校园交友论坛。</p>
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