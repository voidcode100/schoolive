package com.schoolive.controllers;

import com.google.gson.JsonObject;
import com.schoolive.beans.LikeBean;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.LikeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
    private LikeDao likeDao = new LikeDao();

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

        // 获取帖子ID
        String postIdParam = request.getParameter("postId");
        if (postIdParam == null || postIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"帖子ID不能为空\"}");
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

        // 添加点赞
        LikeBean like = new LikeBean();
        like.setPostId(postId);
        like.setUserId(user.getUserId());

        boolean isLiked = likeDao.addLike(like);

        // 返回结果
        response.setContentType("application/json; charset=UTF-8");
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", isLiked);
        jsonResponse.addProperty("likes", likeDao.getLikeCount(postId));
        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        // 获取帖子ID
        String postIdParam = request.getParameter("postId");
        if (postIdParam == null || postIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"帖子ID不能为空\"}");
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

        // 取消点赞
        boolean isRemoved = likeDao.removeLike(postId, user.getUserId());

        // 返回结果
        response.setContentType("application/json; charset=UTF-8");
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", isRemoved);
        jsonResponse.addProperty("likes", likeDao.getLikeCount(postId));
        response.getWriter().write(jsonResponse.toString());
    }
}
