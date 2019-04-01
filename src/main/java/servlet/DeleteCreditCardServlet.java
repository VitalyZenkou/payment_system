package servlet;

import model.dto.User;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static util.ServletConstant.*;

@WebServlet("/" + DELETE_LOCATION)
public class DeleteCreditCardServlet extends HttpServlet {

    private CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        boolean success = customerService.deleteCreditCardByNumber(req.getParameter("card_number"), user);
        if (success) {
            resp.sendRedirect("/" + HOME_LOCATION);
        }
    }
}
