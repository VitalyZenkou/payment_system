package servlet;

import model.dto.CreditCard;
import model.dto.User;
import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static util.ServletConstant.HOME_LOCATION;
import static util.ServletConstant.UNBLOCK_LOCATION;

@WebServlet("/" + UNBLOCK_LOCATION)
public class UnblockCreditCardServlet extends HttpServlet {

    private AdminService adminService = AdminService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<CreditCard> creditCardOptional = adminService
                .getCustomerService()
                .getCreditCardByNumber(req.getParameter("card_number"));
        creditCardOptional.ifPresent(creditCard -> {
            Optional<User> userOptional = adminService.getCustomerService().getUserById(creditCard.getUserId());
            if (userOptional.isPresent()) {
                boolean success = adminService.unblockCreditCard(creditCard, userOptional.get());
                if (success) {
                    try {
                        resp.sendRedirect("/" + HOME_LOCATION);
                    } catch (IOException e) {
                        System.err.println("Cant redirect to home page\n" + e.toString());
                    }
                }
            }
        });
    }
}
