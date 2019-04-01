package service;

import dao.UserDao;
import model.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class UserService {

    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    static UserService getInstance() {
        return INSTANCE;
    }

    void save(User user, Connection connection) throws SQLException {
        UserDao.getInstance().save(user, connection);
    }

    Optional<User> getById(int id) {
        return UserDao.getInstance().getById(id);
    }

    Optional<User> login(String login, String password) {
        return UserDao.getInstance().login(login, password);
    }

    /**
     * use in admin service
     *
     * @return List<User></User>
     */
    List<User> getAll() {
        return UserDao.getInstance().getAll();
    }

    /**
     * use ia admin service
     *
     * @param user
     * @return
     */
    boolean delete(User user) {
        return UserDao.getInstance().delete(user);
    }

    /**
     * use ia admin service
     *
     * @param
     * @return
     */
    List<User> getWithoutCard(){
        return UserDao.getInstance().getWithoutCreditCard();
    }
}
