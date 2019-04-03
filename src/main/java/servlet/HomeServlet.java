package servlet;

import helper.PathHelper;
import model.dto.CreditCard;
import model.dto.Payment;
import model.dto.User;
import service.AdminService;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static util.ServletConstant.*;

@WebServlet("/" + HOME_LOCATION)
public class HomeServlet extends HttpServlet {

    private CustomerService service = CustomerService.getInstance();
    private AdminService adminService = AdminService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        if (user.isAdministrator()) {
            List<User> usersWithoutCard = adminService.getUserWithoutCard();
            List<CreditCard> creditCards = adminService.getAllCreditCards();
            List<User> allUsers = adminService.getAllUsers();
            req.setAttribute(USERS, usersWithoutCard);
            req.setAttribute(CARDS, creditCards);
            req.setAttribute(USERS + "a", allUsers);
        } else {
            List<CreditCard> creditCards = service.getCreditCardsByUser(user);
            List<Payment> payments = service.getPaymentsByUser(user);
            req.setAttribute(CARDS, creditCards);
            req.setAttribute(PAYMENTS, payments);
        }
        req.setAttribute(USER, user);
        getServletContext().getRequestDispatcher(PathHelper.getPath(HOME_LOCATION))
                .forward(req, resp);
    }
}
