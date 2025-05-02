package com.schoolive.controllers;

import com.schoolive.beans.UserBean;
import com.schoolive.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求的字符编码为 UTF-8
        request.setCharacterEncoding("UTF-8");

        // 获取表单提交的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 调用 UserDao 验证用户
        UserBean user = userDao.loginUser(username, password);

        if (user != null) {
            // 登录成功，将用户信息存入 Session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 重定向到主页
            response.sendRedirect("home.jsp");
        } else {
            // 登录失败，设置错误信息并转发回登录页面
            request.setAttribute("error", "用户名不存在或密码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
