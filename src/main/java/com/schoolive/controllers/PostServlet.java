package com.schoolive.controllers;

import com.google.gson.Gson;
import com.schoolive.beans.PostBean;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.PostDao;
import com.schoolive.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/posts")
public class PostServlet extends HttpServlet {
    private PostDao postDao = new PostDao();
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为 JSON
        response.setContentType("application/json; charset=UTF-8");

        // 获取当前登录用户
        HttpSession session = request.getSession(false);
        UserBean currentUser = (UserBean) session.getAttribute("user");
        int userId = (currentUser != null) ? currentUser.getUserId() : 0;

        // 调用 PostDao 获取所有帖子（包含点赞数和用户点赞状态）
        List<PostBean> posts = postDao.getAllPostsWithLikes(userId);

        // 为每个帖子获取发布者信息
        for (PostBean post : posts) {
            UserBean user = userDao.getUserById(post.getUserId());
            if (user != null) {
                post.setAuthor(user.getUsername()); // 设置作者用户名
            } else {
                post.setAuthor("未知用户"); // 如果用户不存在，设置默认值
            }
        }

        // 将帖子列表转换为 JSON 格式
        Gson gson = new Gson();
        String json = gson.toJson(posts);

        // 将 JSON 数据写入响应
        response.getWriter().write(json);
    }
}
