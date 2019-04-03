package dao;

import exception.ConnectionException;
import lombok.NonNull;
import model.dto.Payment;
import model.dto.User;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.PreparedStatement.RETURN_GENERATED_KEYS;

public class PaymentDao {

    private static final PaymentDao INSTANCE = new PaymentDao();
    private static final String SAVE_PAYMENT = "INSERT INTO payment(credit_card_number,cost,organization,user_id)" +
            "VALUES (?,?,?,?)";
    private static final String SAVE_TRANSACTION = "INSERT INTO payment(credit_card_number,cost,user_id,to_credit_card_number,is_transaction)"
            + "VALUES(?,?,?,?,?)";
    private static final String GET_BY_USER = "SELECT * FROM payment WHERE user_id = (?)";

    private PaymentDao() {
    }

    public static PaymentDao getInstance() {
        return INSTANCE;
    }

    public List<Payment> getByUser(User user) {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_USER)) {
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                payments.add(Payment.builder()
                        .id(resultSet.getInt("id"))
                        .userId(resultSet.getInt("user_id"))
                        .creditCardNumber(resultSet.getString("credit_card_number"))
                        .cost(resultSet.getDouble("cost"))
                        .organization(resultSet.getString("organization"))
                        .toCreditCard(resultSet.getString("to_credit_card_number"))
                        .transaction(resultSet.getBoolean("is_transaction"))
                        .build());
            }
        } catch (SQLException e) {
            throw new ConnectionException();
        }
        return payments;
    }

    public void savePayment(@NonNull Payment payment, User user, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE_PAYMENT, RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, payment.getCreditCardNumber());
        preparedStatement.setDouble(2, payment.getCost());
        preparedStatement.setString(3, payment.getOrganization());
        preparedStatement.setLong(4, user.getId());
        preparedStatement.executeUpdate();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        if (keys.next()) {
            payment.setId(keys.getInt(1));
        }
    }

    public void saveTransaction(@NonNull Payment payment, User user, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TRANSACTION, RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, payment.getCreditCardNumber());
        preparedStatement.setDouble(2, payment.getCost());
        preparedStatement.setLong(3, user.getId());
        preparedStatement.setString(4, payment.getToCreditCard());
        preparedStatement.setBoolean(5, payment.isTransaction());
        preparedStatement.executeUpdate();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        if (keys.next()) {
            payment.setId(keys.getInt(1));
        }
    }
}
