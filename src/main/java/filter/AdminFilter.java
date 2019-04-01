package filter;

import model.dto.User;
import util.ServletConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static util.ServletConstant.ADMIN_LOCATION;

@WebFilter("/" + ADMIN_LOCATION)
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        if (isAdmin(request)) {
            servletResponse.sendRedirect("/" + ADMIN_LOCATION);
            chain.doFilter(request, response);
        } else {
            servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean isAdmin(ServletRequest request) {
        User user = ((User) ((HttpServletRequest) request).getSession().getAttribute(ServletConstant.USER));
        return Objects.nonNull(user) && user.isAdministrator();
    }
}
