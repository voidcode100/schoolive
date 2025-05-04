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
<body id="profile-page">
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

        <!-- 修改用户信息表单 -->
        <section>
            <h2>修改个人信息</h2>
            <form id="updateUserForm" action="updateUser" method="post">
                <label for="username">用户名：</label>
                <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>

                <label for="email">邮箱：</label>
                <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>

                <label for="bio">简介：</label>
                <textarea id="bio" name="bio" rows="4"><%= user.getBio() %></textarea>

                <button type="submit">保存修改</button>
            </form>
        </section>

        <!-- 我的帖子 -->
        <section>
            <h2>
                <button class="toggle-button" onclick="toggleSection('userPostList', this)">
                    <img class="toggle-icon" src="images/fold-49.png" alt="展开/收起" />
                    我的帖子
                </button>
            </h2>
            <div id="userPostList" class="post-list" style="display: none;">
                <!-- 动态加载用户帖子 -->
            </div>
        </section>

        <!-- 收藏帖子 -->
        <section>
            <h2>
                <button class="toggle-button" onclick="toggleSection('favoritePostList', this)">
                    <img class="toggle-icon" src="images/fold-49.png" alt="展开/收起" />
                    收藏帖子
                </button>
            </h2>
            <div id="favoritePostList" class="post-list" style="display: none;">
                <!-- 动态加载收藏帖子 -->
            </div>
        </section>
    </main>
    <footer>
        <p>&copy; 2025 Schoolive. All rights reserved.</p>
    </footer>
    <script src="js/scripts.js"></script>
</body>
</html>