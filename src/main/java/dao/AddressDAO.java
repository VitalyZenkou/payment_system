package dao;

import exception.ConnectionException;
import lombok.NonNull;
import model.dto.Address;
import model.dto.User;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDAO {

    private static final AddressDAO INSTANCE = new AddressDAO();
    private static final String SAVE = "INSERT INTO address (user_id,city,street,phone_number,house_number,flat_number)"
            + "VALUES (?,?,?,?,?,?)";
    private static final String GET_BY_USER = "SELECT * FROM address WHERE user_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM address WHERE id = (?)";
    private static final String DELETE_BY_ID = "DELETE FROM address WHERE id = (?)";

    private AddressDAO() {
    }

    public static AddressDAO getInstance() {
        return INSTANCE;
    }

    public List<Address> getByUser(User user) {
        List<Address> address = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_USER)) {
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                address.add(getAddress(resultSet));
            }
        } catch (SQLException e) {
            throw new ConnectionException();
        }
        return address;
    }

    public boolean save(@NonNull Address address, User user) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            initiateStatment(user, address, preparedStatement).executeUpdate();
        } catch (SQLException e) {
            System.err.println(String.format("The address wasn't saved for user %s!\n%s",
                    user.getLogin(), e));
            return false;
        }
        return true;
    }

    public void saveFromRegistration(@NonNull Address address, User user, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
        initiateStatment(user, address, preparedStatement).executeUpdate();
    }

    public Optional<Address> getById(int id) {
        Address address = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                address = getAddress(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Can't address with id " + id);
        }
        return Optional.ofNullable(address);
    }

    public boolean deleteById(int id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(String.format("Adrees with %d wasn't deleted!", id));
            return false;
        }
        return true;
    }

    private Address getAddress(ResultSet resultSet) throws SQLException {
        return Address.builder()
                .id(resultSet.getInt("id"))
                .userId(resultSet.getInt("user_id"))
                .city(resultSet.getString("city"))
                .street(resultSet.getString("street"))
                .flatNumber(resultSet.getInt("flat_number"))
                .houseNumber(resultSet.getInt("house_number"))
                .phoneNumber(resultSet.getString("phone_number"))
                .build();
    }

    private PreparedStatement initiateStatment(User user, Address address, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, user.getId());
        preparedStatement.setString(2, address.getCity());
        preparedStatement.setString(3, address.getStreet());
        preparedStatement.setString(4, address.getPhoneNumber());
        preparedStatement.setInt(5, address.getHouseNumber());
        preparedStatement.setInt(6, address.getFlatNumber());
        return preparedStatement;
    }
}
