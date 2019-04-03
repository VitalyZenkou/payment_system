package servlet;

import model.dto.CreditCard;
import model.dto.User;
import service.AdminService;
import util.CreditCardDataGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static util.ServletConstant.*;

@WebServlet("/" + ADD_CARD_LOCATION)
public class AddCardServlet extends HttpServlet {

    private AdminService adminService = AdminService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User mainUser = (User) req.getSession().getAttribute(USER);
        Optional<User> optionalUser = adminService.getCustomerService()
                .getUserById(Integer.valueOf(req.getParameter("number")));
        CreditCard creditCard = CreditCard.builder()
                .validityDate(req.getParameter("date"))
                .creditCardType("visa")
                .balance(0d)
                .blocked(false)
                .cvv(CreditCardDataGenerator.generateCvv())
                .number(CreditCardDataGenerator.generateCreditCardNumber())
                .pinCode(CreditCardDataGenerator.generatePinCode())
                .build();
        req.setAttribute(USER, mainUser);
        optionalUser.ifPresent(user -> {
            boolean success = adminService.saveCreditCard(creditCard, user);
            if (success) {
                try {
                    resp.sendRedirect("/" + HOME_LOCATION);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
