package com.schoolive.controllers;

import com.google.gson.Gson;
import com.schoolive.beans.PostBean;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.PostDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/userPosts")
public class UserPostsServlet extends HttpServlet {
    private PostDao postDao = new PostDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为 JSON
        response.setContentType("application/json; charset=UTF-8");

        // 获取当前登录用户
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"用户未登录\"}");
            return;
        }

        // 获取用户的帖子
        List<PostBean> posts = postDao.getPostsByUserId(user.getUserId());

        // 将帖子列表转换为 JSON 格式
        Gson gson = new Gson();
        String json = gson.toJson(posts);

        // 将 JSON 数据写入响应
        response.getWriter().write(json);
    }
}
