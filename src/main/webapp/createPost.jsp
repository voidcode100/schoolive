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
    <title>发布帖子 - Schoolive</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <header>
        <h1>发布帖子</h1>
        <nav>
            <a href="home.jsp">返回主页</a>
        </nav>
    </header>
    <main>
        <div class="container">
            <form id="createPostForm" action="createPost" method="post">
                <label for="title">标题：</label>
                <input type="text" id="title" name="title" placeholder="请输入帖子标题" required>
                
                <label for="content">内容：</label>
                <textarea id="content" name="content" rows="8" placeholder="请输入帖子内容" required></textarea>
                
                <button type="submit">发布</button>
            </form>
        </div>
    </main>
    <footer>
        <p>&copy; 2025 Schoolive. All rights reserved.</p>
    </footer>
</body>
</html>