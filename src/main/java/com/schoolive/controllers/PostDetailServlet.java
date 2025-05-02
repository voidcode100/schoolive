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

@WebServlet("/postDetail")
public class PostDetailServlet extends HttpServlet {
    private PostDao postDao = new PostDao();
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为 JSON
        response.setContentType("application/json; charset=UTF-8");

        // 获取帖子ID
        String postIdParam = request.getParameter("postId");
        if (postIdParam == null || postIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"帖子ID不能为空\"}");
            return;
        }

        int postId;
        try {
            postId = Integer.parseInt(postIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"无效的帖子ID\"}");
            return;
        }

        // 获取帖子详情
        PostBean post = postDao.getPostById(postId);
        if (post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"帖子不存在\"}");
            return;
        }

        // 获取发布者信息
        UserBean user = userDao.getUserById(post.getUserId());
        if (user != null) {
            post.setAuthor(user.getUsername()); // 设置发布者用户名
        } else {
            post.setAuthor("未知用户"); // 如果用户不存在，设置默认值
        }

        // 将帖子详情转换为 JSON 格式
        Gson gson = new Gson();
        String json = gson.toJson(post);

        // 返回 JSON 数据
        response.getWriter().write(json);
    }
}
