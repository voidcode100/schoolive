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
    <title>帖子详情 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <header>
        <h1>Schoolive</h1>
        <nav>
            <a href="home.jsp">主页</a>
            <a href="profile.jsp">个人主页</a>
            <a href="logout">退出登录</a>
        </nav>
    </header>
    <main>
        <article>
            <!-- 动态加载帖子详情 -->
        </article>
        <section>
            <h3>评论</h3>
            <form id="commentForm" action="comment" method="post">
                <textarea id="commentInput" name="content" rows="4" placeholder="发表评论..." required></textarea>
                <button type="submit">提交评论</button>
            </form>
            <ul id="commentList" class="comments" data-post-id="1">
                <!-- 动态加载评论 -->
            </ul>
        </section>
    </main>
    <footer>
        <p>&copy; 2025 Schoolive. All rights reserved.</p>
    </footer>
    <script src="js/scripts.js"></script>
</body>
</html>