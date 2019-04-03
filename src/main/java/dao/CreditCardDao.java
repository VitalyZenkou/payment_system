package dao;

import exception.ConnectionException;
import lombok.NonNull;
import model.dto.CreditCard;
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

public class CreditCardDao {

    private static final CreditCardDao INSTANCE = new CreditCardDao();
    private static final String SAVE = "INSERT INTO credit_card(user_id,number,validity_date,pin_code,cvv,balance,is_blocked,credit_card_type)" +
            "VALUES (?,?,?,?,?,?,?,?)";
    private static final String GET_BY_USER = "SELECT * FROM credit_card WHERE user_id = ?";
    private static final String BLOCK_UNBLOCK_CREDIT_CARD = "UPDATE credit_card SET is_blocked = ? WHERE number = ?";
    private static final String GET_BY_CREDIT_CARD_NUMBER = "SELECT * FROM credit_card WHERE number = ?";
    private static final String GET_BY_ID = "SELECT * FROM credit_card WHERE id = (?)";
    private static final String DELETE_BY_ID = "DELETE FROM credit_card WHERE id = (?) AND user_id = ?";
    private static final String DELETE_BY_NUMBER = "DELETE FROM credit_card WHERE number = (?) AND user_id = (?)";
    private static final String UPDATE_BALANCE_BY_CARD = "UPDATE credit_card SET balance = ? WHERE number = ?";
    private static final String GET_ALL_CREDIT_CARD = "SELECT * FROM credit_card";

    private CreditCardDao() {
    }

    public static CreditCardDao getInstance() {
        return INSTANCE;
    }

    public List<CreditCard> getByUser(User user) {
        List<CreditCard> creditCards = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_USER)) {
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                creditCards.add(getCreditCards(resultSet));
            }
        } catch (SQLException e) {
            throw new ConnectionException();
        }
        return creditCards;
    }

    public List<CreditCard> getAll() {
        List<CreditCard> creditCards = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CREDIT_CARD)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                creditCards.add(getCreditCards(resultSet));
            }
        } catch (SQLException e) {
            throw new ConnectionException();
        }
        return creditCards;
    }

    public boolean save(@NonNull CreditCard creditCard, User user) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, creditCard.getNumber());
            preparedStatement.setString(3, creditCard.getValidityDate());
            preparedStatement.setInt(4, creditCard.getPinCode());
            preparedStatement.setInt(5, creditCard.getCvv());
            preparedStatement.setDouble(6, creditCard.getBalance());
            preparedStatement.setBoolean(7, creditCard.isBlocked());
            preparedStatement.setString(8, creditCard.getCreditCardType());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                creditCard.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println(String.format("The credit card wasn't saved for user %s!\n",
                    user.getLogin()) + e.toString());
            return false;
        }
        return true;
    }

    public boolean blockCreditCard(CreditCard creditCard, User user, boolean state) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(BLOCK_UNBLOCK_CREDIT_CARD)) {
            preparedStatement.setBoolean(1, state);
            preparedStatement.setLong(2, user.getId());
            preparedStatement.setString(2, creditCard.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(String.format("The credit cards number %s wasn't blocked!",
                    creditCard.getNumber()));
            return false;
        }
        return true;
    }

    public Optional<CreditCard> getById(int id) {
        CreditCard creditCard = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                creditCard = getCreditCards(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Can't find user with id " + id);
        }
        return Optional.ofNullable(creditCard);
    }

    public Optional<CreditCard> getByNumber(String number) {
        CreditCard creditCard = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CREDIT_CARD_NUMBER)) {
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                creditCard = getCreditCards(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Can't find user with credit card number " + number);
        }
        return Optional.ofNullable(creditCard);
    }

    public void updateBalance(String creditCardNumber, double updatedBalance, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BALANCE_BY_CARD);
        preparedStatement.setDouble(1, updatedBalance);
        preparedStatement.setString(2, creditCardNumber);
        preparedStatement.executeUpdate();
    }

    public boolean deleteById(int id, User user) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(String.format("Credit cards %d wasn't deleted!", id));
            return false;
        }
        return true;
    }

    public boolean deleteByNumber(String number, User user) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NUMBER)) {
            preparedStatement.setString(1, number);
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(String.format("Credit cards number %s  wasn't deleted!", number));
            return false;
        }
        return true;
    }

    private CreditCard getCreditCards(ResultSet resultSet) throws SQLException {
        return (CreditCard.builder()
                .id(resultSet.getInt("id"))
                .userId(resultSet.getInt("user_id"))
                .number(resultSet.getString("number"))
                .validityDate(resultSet.getString("validity_date"))
                .pinCode(resultSet.getInt("pin_code"))
                .cvv(resultSet.getInt("cvv"))
                .balance(resultSet.getDouble("balance"))
                .blocked(resultSet.getBoolean("is_blocked"))
                .creditCardType(resultSet.getString("credit_card_type"))
                .build());
    }
}

