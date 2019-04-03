package servlet;

import helper.PathHelper;
import model.dto.User;
import service.CustomerService;
import util.ServletConstant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static util.ServletConstant.*;

@WebServlet("/" + LOGIN_LOCATION)
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PathHelper.getPath(LOGIN_LOCATION)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> userOptional = CustomerService.getInstance()
                .login(req.getParameter(LOGIN), req.getParameter(PASSWORD));
        User user = null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            req.getSession().setAttribute(ServletConstant.USER, user);
            resp.sendRedirect("/" + HOME_LOCATION);
        } else {
            resp.getWriter().println("Вы ввели неверный логин или пароль");
            try {
                Thread.sleep(2000);
                getServletContext().getRequestDispatcher(PathHelper.getPath(LOGIN_LOCATION)).forward(req, resp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
