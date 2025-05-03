package com.schoolive.controllers;

import com.google.gson.Gson;
import com.schoolive.beans.FavoriteBean;
import com.schoolive.beans.PostBean;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.FavoriteDao;
import com.schoolive.dao.PostDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/favoritePosts")
public class FavoritePostServlet extends HttpServlet {
    private FavoriteDao favoriteDao = new FavoriteDao();
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

        // 获取用户收藏的帖子列表
        List<FavoriteBean> favoriteBeans = favoriteDao.getFavoritePostsByUserId(user.getUserId());
        List<PostBean> favoritePosts = new ArrayList<>();

        // 根据收藏记录中的 postId 查询帖子信息
        for (FavoriteBean favorite : favoriteBeans) {
            PostBean post = postDao.getPostById(favorite.getPostId());
            if (post != null) {
                favoritePosts.add(post);
            }
        }

        // 将帖子列表转换为 JSON 格式
        Gson gson = new Gson();
        String json = gson.toJson(favoritePosts);

        // 返回 JSON 数据
        response.getWriter().write(json);
    }
}
