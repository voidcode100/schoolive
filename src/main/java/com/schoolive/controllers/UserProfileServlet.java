package com.schoolive.controllers;

import com.google.gson.Gson;
import com.schoolive.beans.UserBean;
import com.schoolive.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的内容类型为 JSON
        response.setContentType("application/json; charset=UTF-8");

        // 获取用户ID
        String userIdParam = request.getParameter("userId");
        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"用户ID不能为空\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"无效的用户ID\"}");
            return;
        }

        // 获取用户信息
        UserBean user = userDao.getUserById(userId);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"用户不存在\"}");
            return;
        }

        // 返回用户信息
        Gson gson = new Gson();
        String json = gson.toJson(user);
        response.getWriter().write(json);
    }
}
