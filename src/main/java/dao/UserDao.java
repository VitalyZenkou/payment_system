package dao;

import exception.ConnectionException;
import lombok.NonNull;
import model.dto.User;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.PreparedStatement.RETURN_GENERATED_KEYS;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private static final String SAVE = "INSERT INTO \"user\" (login,password,name,surname,is_administrator)" + "VALUES (?,?,?,?,?)";
    private static final String LOGIN = "SELECT * FROM \"user\"" + "WHERE login = ? AND password = ?";
    private static final String GET_BY_ID = "SELECT * FROM \"user\" " + "WHERE id = (?)";
    private static final String GET_WITHOUT_CARD = "SELECT * FROM \"user\" WHERE \"user\".id NOT IN (SELECT user_id FROM credit_card)";
    private static final String DELETE_USER = "DELETE FROM \"user\" " + "WHERE id = (?)";
    private static final String DELETE_ADDRESS = "DELETE FROM address " + " WHERE user_id = (?)";
    private static final String DELETE_CREDIT_CARD = "DELETE FROM credit_card " + " WHERE user_id = (?)";
    private static final String DELETE_PAYMENT = "DELETE FROM payment " + " WHERE user_id = (?)";
    private static final String GET_ALL = "SELECT * FROM \"user\"";

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public List<User> getWithoutCreditCard() {
        return getUsers(GET_WITHOUT_CARD);
    }

    public Optional<User> getById(int id) {
        User user = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUser(resultSet);
            } else {
                System.err.println(String.format("There isn't a user with id %d", id));
            }
        } catch (SQLException e) {
            System.err.println(e);
            throw new ConnectionException();
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> login(String login, String password) {
        User user = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUser(resultSet);

            } else {
                System.err.println(String.format("There isn't a user with login %s", login));
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.toString());
        }
        return Optional.ofNullable(user);
    }

    public List<User> getAll() {
        return getUsers(GET_ALL);
    }

    public void save(@NonNull User user, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getSurname());
        preparedStatement.setBoolean(5, user.isAdministrator());
        preparedStatement.executeUpdate();
        ResultSet keys = preparedStatement.getGeneratedKeys();
        if (keys.next()) {
            user.setId(keys.getLong(1));
        }
    }

    public boolean delete(User user) {
        boolean result;
        Connection connection = null;
        PreparedStatement userPs = null;
        PreparedStatement addressPs = null;
        PreparedStatement creditCardsPs = null;
        PreparedStatement paymentsPs = null;
        try {
            connection = ConnectionManager.get();
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            addressPs = delete(connection, DELETE_ADDRESS, user.getId());
            creditCardsPs = delete(connection, DELETE_CREDIT_CARD, user.getId());
            paymentsPs = delete(connection, DELETE_PAYMENT, user.getId());
            userPs = delete(connection, DELETE_USER, user.getId());

            connection.commit();
            result = true;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                System.err.println(e1);
                throw new ConnectionException();
            }
            System.err.println(e);
            throw new ConnectionException();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (userPs != null) {
                    userPs.close();
                }
                if (addressPs != null) {
                    addressPs.close();
                }
                if (creditCardsPs != null) {
                    creditCardsPs.close();
                }
                if (paymentsPs != null) {
                    paymentsPs.close();
                }
            } catch (SQLException e) {
                throw new ConnectionException();
            }
        }
        return result;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(Long.valueOf(resultSet.getString("id")))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .name(resultSet.getString("name"))
                .surname(resultSet.getString("surname"))
                .administrator(resultSet.getBoolean("is_administrator"))
                .build();
    }

    private List<User> getUsers(String sql) {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            throw new ConnectionException();
        }
        return users;
    }

    private PreparedStatement delete(Connection connection, String sql, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
        return preparedStatement;
    }
}