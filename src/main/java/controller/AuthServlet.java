package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/api/auth/user")
public class AuthServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");

        if (session != null && session.getAttribute("accountId") != null) {

            response.getWriter().write("{\"loggedIn\": true, \"name\": \"" + session.getAttribute("name") + "\"}");
        } else {
            response.getWriter().write("{\"loggedIn\": false}");
        }
    }
}