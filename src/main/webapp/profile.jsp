<%@ page contentType="text/html; charset=UTF-8" %>
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
        <h1>Schoolive</h1>
        <nav>
            <a href="home.jsp">主页</a>
            <a href="logout">退出登录</a>
        </nav>
    </header>
    <main>
        <section>
            <h2>个人信息</h2>
            <div class="profile-info">
                <p><strong>用户名：</strong> 示例用户</p>
                <p><strong>邮箱：</strong> example@example.com</p>
                <p><strong>简介：</strong> 这是一个示例简介。</p>
            </div>
        </section>
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