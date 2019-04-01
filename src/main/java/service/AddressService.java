package service;

import dao.AddressDAO;
import model.dto.Address;
import model.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class AddressService {

    private static final AddressService INSTANCE = new AddressService();

    private AddressService() {
    }

    static AddressService getInstance() {
        return INSTANCE;
    }

    List<Address> getByUser(User user) {
        return AddressDAO.getInstance().getByUser(user);
    }

    boolean save(Address address, User user) {
        return AddressDAO.getInstance().save(address, user);
    }

    Optional<Address> getById(int id) {
        return AddressDAO.getInstance().getById(id);
    }

    boolean deleteById(int id) {
        return AddressDAO.getInstance().deleteById(id);
    }

    void saveFromRegistration(User user, Address address, Connection connection) throws SQLException {
        AddressDAO.getInstance().saveFromRegistration(address, user, connection);
    }
}
