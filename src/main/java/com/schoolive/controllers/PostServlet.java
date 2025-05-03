package com.schoolive.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.schoolive.beans.PostBean;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.PostDao;
import com.schoolive.dao.UserDao;
import com.schoolive.dao.CommentDao;
import com.schoolive.dao.LikeDao;
import com.schoolive.dao.FavoriteDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/posts")
public class PostServlet extends HttpServlet {
    private PostDao postDao = new PostDao();
    private UserDao userDao = new UserDao();
    private CommentDao commentDao = new CommentDao();
    private LikeDao likeDao = new LikeDao();
    private FavoriteDao favoriteDao = new FavoriteDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为 JSON
        response.setContentType("application/json; charset=UTF-8");

        // 获取当前登录用户
        HttpSession session = request.getSession(false);
        UserBean currentUser = (UserBean) session.getAttribute("user");
        int userId = (currentUser != null) ? currentUser.getUserId() : 0;

        // 调用 PostDao 获取所有帖子（包含点赞数和用户点赞状态）
        List<PostBean> posts = postDao.getAllPostsWithLikesAndFavorites(userId);

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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为 JSON
        response.setContentType("application/json; charset=UTF-8");

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

        // 删除与帖子相关的评论、点赞、收藏记录
        commentDao.deleteCommentsByPostId(postId); // 即使没有评论也不会影响结果
        likeDao.removeLikesByPostId(postId);      // 即使没有点赞也不会影响结果
        favoriteDao.removeFavoritesByPostId(postId); // 即使没有收藏也不会影响结果

        // 删除帖子
        boolean postDeleted = postDao.deletePost(postId);

        // 返回结果
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", postDeleted); // 只根据帖子是否删除成功返回结果
        if (!postDeleted) {
            jsonResponse.addProperty("message", "帖子删除失败");
        }
        response.getWriter().write(jsonResponse.toString());

        
    }
}
