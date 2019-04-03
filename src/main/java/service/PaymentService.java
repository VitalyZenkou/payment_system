package service;

import dao.PaymentDao;
import model.dto.Payment;
import model.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class PaymentService {

    private static final PaymentService INSTANCE = new PaymentService();

    private PaymentService() {
    }

    static PaymentService getInstance() {
        return INSTANCE;
    }

    List<Payment> getByUser(User user) {
        return PaymentDao.getInstance().getByUser(user);
    }

    void savePayment(Payment payment, User user, Connection connection) throws SQLException {
        PaymentDao.getInstance().savePayment(payment, user, connection);
    }

    void saveTransaction(Payment payment, User user, Connection connection) throws SQLException {
        PaymentDao.getInstance().saveTransaction(payment, user, connection);
    }
}
