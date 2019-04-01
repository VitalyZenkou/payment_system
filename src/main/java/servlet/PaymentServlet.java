package servlet;

import model.dto.CreditCard;
import model.dto.Payment;
import model.dto.User;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static util.ServletConstant.*;

@WebServlet("/" + PAYMENT_LOCATION)
public class PaymentServlet extends HttpServlet {

    private CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER);
        Payment payment = Payment.builder()
                .creditCardNumber(req.getParameter("card_number"))
                .cost(Double.valueOf(req.getParameter("cost")))
                .organization(req.getParameter("org"))
                .build();
        List<CreditCard> creditCards = customerService.getCreditCardsByUser(user);
        Optional<CreditCard> optionalCreditCard = creditCards.stream()
                .filter(card -> card.getNumber().equals(payment.getCreditCardNumber()))
                .findFirst();
        optionalCreditCard.ifPresent(creditCard -> customerService.savePayment(payment, user, creditCard));
        resp.sendRedirect("/" + HOME_LOCATION);
    }
}
