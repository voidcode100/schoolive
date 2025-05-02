package com.schoolive.controllers;

import com.schoolive.beans.PostBean;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.PostDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/createPost")
public class CreatePostServlet extends HttpServlet {
    private PostDao postDao = new PostDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求的字符编码为 UTF-8
        request.setCharacterEncoding("UTF-8");

        // 获取表单提交的数据
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // 获取当前登录用户
        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = ((UserBean) userObj).getUserId();

        // 创建帖子对象
        PostBean post = new PostBean();
        post.setUserId(userId);
        post.setTitle(title);
        post.setContent(content);

        // 调用 DAO 保存帖子
        boolean isCreated = postDao.createPost(post);

        if (isCreated) {
            // 发布成功，重定向到主页
            response.sendRedirect("home.jsp");
        } else {
            // 发布失败，返回发布页面并显示错误信息
            request.setAttribute("error", "发布失败，请重试！");
            request.getRequestDispatcher("createPost.jsp").forward(request, response);
        }
    }
}