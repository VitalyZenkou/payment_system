package filter;

import model.dto.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static util.ServletConstant.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private HttpServletRequest servletRequest;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        servletRequest = (HttpServletRequest) request;
        String loginURI = servletRequest.getContextPath() + "/" + LOGIN_LOCATION;
        String registrationURI = servletRequest.getContextPath() + "/" + REGISTRATION_LOCATION;
        if (servletRequest.getRequestURI().equals(registrationURI) || isExist()) {
            chain.doFilter(request, response);
        } else if (!isExist() && !servletRequest.getRequestURI().equals(loginURI)) {
            ((HttpServletResponse) response).sendRedirect("/" + LOGIN_LOCATION);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isExist() {
        User user = (User) servletRequest.getSession().getAttribute(USER);
        return Objects.nonNull(user);
    }
}
