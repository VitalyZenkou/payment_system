package servlet;

import helper.PathHelper;
import model.dto.Address;
import model.dto.User;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static util.ServletConstant.*;

@WebServlet("/" + REGISTRATION_LOCATION)
public class RegistrationServlet extends HttpServlet {

    private CustomerService customerService = CustomerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(PathHelper.getPath(REGISTRATION_LOCATION))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = User.builder()
                .login(req.getParameter(LOGIN))
                .password(req.getParameter(PASSWORD))
                .name(req.getParameter(NAME))
                .surname(req.getParameter(SURNAME))
                .build();
        Address address = Address.builder()
                .city(req.getParameter(CITY))
                .street(req.getParameter(STREET))
                .houseNumber(Integer.valueOf(req.getParameter(HOUSE)))
                .flatNumber(Integer.valueOf(req.getParameter(FLAT)))
                .phoneNumber(req.getParameter(PHONE))
                .build();
        boolean success = customerService.saveUserFromRegistration(user, address);
        if (success) {
            getServletContext().getRequestDispatcher(PathHelper.getPath(LOGIN)).forward(req, resp);
        } else {

        }
    }
}
