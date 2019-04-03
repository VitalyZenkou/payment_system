package servlet;

import model.dto.CreditCard;
import model.dto.User;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static util.ServletConstant.*;

@WebServlet("/" + BLOCK_LOCATION)
public class BlockCreditCardServlet extends HttpServlet {

    private CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<CreditCard> creditCardOptional = customerService
                .getCreditCardByNumber(req.getParameter("card_number"));
        if (creditCardOptional.isPresent()) {
            CreditCard creditCard = creditCardOptional.get();
            Optional<User> userCardHolderOptional = customerService.getUserById(creditCard.getUserId());
            if (userCardHolderOptional.isPresent()) {
                boolean success = customerService.blockCreditCard(creditCardOptional.get(), userCardHolderOptional.get());
                if (success) {
                    resp.sendRedirect("/" + HOME_LOCATION);
                }
            }
        }
    }
}
