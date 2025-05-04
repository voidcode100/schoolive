package com.schoolive.controllers;

import com.google.gson.Gson;
import com.schoolive.beans.CommentBean;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.CommentDao;
import com.schoolive.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private CommentDao commentDao = new CommentDao();
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求的字符编码为 UTF-8
        request.setCharacterEncoding("UTF-8");

        // 获取当前登录用户
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"message\": \"用户未登录\"}");
            return;
        }

        // 获取评论内容和帖子ID
        String content = request.getParameter("content");
        String postIdParam = request.getParameter("postId");

        if (content == null || content.trim().isEmpty() || postIdParam == null || postIdParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"评论内容或帖子ID不能为空\"}");
            return;
        }

        int postId;
        try {
            postId = Integer.parseInt(postIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"无效的帖子ID\"}");
            return;
        }

        // 创建评论对象
        CommentBean comment = new CommentBean();
        comment.setPostId(postId);
        comment.setUserId(user.getUserId());
        comment.setContent(content);

        // 添加评论
        boolean isAdded = commentDao.addComment(comment);

        // 返回结果
        response.setContentType("application/json; charset=UTF-8");
        if (isAdded) {
            response.getWriter().write("{\"success\": true, \"message\": \"评论添加成功\", \"author\": \"" + user.getUsername() + "\"}");
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"评论添加失败\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取帖子ID
        int postId = Integer.parseInt(request.getParameter("postId"));

        // 获取评论列表
        List<CommentBean> comments = commentDao.getCommentsByPostId(postId);

        // 为每条评论获取用户信息
        for (CommentBean comment : comments) {
            UserBean user = userDao.getUserById(comment.getUserId());
            if (user != null) {
                comment.setAuthor("<a href='userProfile.jsp?userId=" + user.getUserId() + "'>" + user.getUsername() + "</a>");
            } else {
                comment.setAuthor("未知用户");
            }
        }

        // 将评论列表转换为 JSON 格式
        Gson gson = new Gson();
        String json = gson.toJson(comments);

        // 返回 JSON 数据
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }
}
