package com.schoolive.controllers;

import com.schoolive.beans.UserBean;
import com.schoolive.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求的字符编码为 UTF-8
        request.setCharacterEncoding("UTF-8");

        // 获取当前登录用户
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 获取表单提交的数据
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String bio = request.getParameter("bio");

        // 更新用户对象
        user.setUsername(username);
        user.setEmail(email);
        user.setBio(bio);

        // 调用 UserDao 更新用户信息
        boolean isUpdated = userDao.updateUser(user);

        if (isUpdated) {
            // 更新成功，将用户信息更新到 Session 中
            session.setAttribute("user", user);
            session.setAttribute("successMessage", "个人信息修改成功！");
            response.sendRedirect("profile.jsp");
        } else {
            // 更新失败，返回错误信息
            request.setAttribute("error", "更新失败，请重试！");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }
}
