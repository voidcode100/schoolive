package com.schoolive.controllers;

import com.schoolive.beans.UserBean;
import com.schoolive.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求的字符编码为 UTF-8
        request.setCharacterEncoding("UTF-8");

        // 获取表单提交的用户信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // 创建用户对象
        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password); // 密码应在此处加密存储
        user.setEmail(email);

        // 调用 UserDao 注册用户
        boolean isRegistered = userDao.registerUser(user);

        if (isRegistered) {
            // 注册成功，重定向到登录页面
            response.sendRedirect("login.jsp");
        } else {
            // 注册失败，返回注册页面并显示错误信息
            request.setAttribute("error", "注册失败，请重试！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
