package servlet;

import model.dto.User;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static util.ServletConstant.DELETE_USER_LOCATION;
import static util.ServletConstant.HOME_LOCATION;

@WebServlet("/" + DELETE_USER_LOCATION)
public class DeleteUserServlet extends HttpServlet {

    private AdminService adminService = AdminService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> optionalUser = adminService
                .getCustomerService()
                .getUserById(Integer.valueOf(req.getParameter("number")));
        optionalUser.ifPresent(user -> {
            boolean success = adminService.deleteUser(user);
            if (success) {
                try {
                    resp.sendRedirect("/" + HOME_LOCATION);
                } catch (IOException e) {
                    System.err.println("Can't redirect to home page\n" + e.toString());
                }
            }
        });
    }
}
