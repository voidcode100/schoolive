<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.schoolive.beans.UserBean" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    UserBean user = (UserBean) session.getAttribute("user");
    String userIdParam = request.getParameter("userId");
    if (userIdParam == null || userIdParam.isEmpty()) {
        response.sendRedirect("home.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户简介 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body id="user-profile-page" data-user-id="<%= userIdParam %>">
    <header>
        <h1>Schoolive</h1>
        <nav>
            <a href="home.jsp">主页</a>
            <a href="profile.jsp">个人主页</a>
            <a href="logout">退出登录</a>
        </nav>
    </header>
    <main>
        <!-- 返回按钮 -->
        <img class="back-icon" src="images/left-50.png" alt="返回" onclick="goBackToPost()" />

        <section class="profile-info">
            <h2>用户简介</h2>
            <p><strong>用户名：</strong> <span id="username"></span></p>
            <p><strong>邮箱：</strong> <span id="email"></span></p>
            <p><strong>简介：</strong> <span id="bio"></span></p>
        </section>

        <section>
            <h2>
                <button class="toggle-button" onclick="toggleSection('userPostList', this)">
                    <img class="toggle-icon" src="images/fold-49.png" alt="展开/收起" />
                    用户的帖子
                </button>
            </h2>
            <div id="userPostList" class="post-list" style="display: none;">
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