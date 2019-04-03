package service;

import dao.CreditCardDao;
import model.dto.CreditCard;
import model.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class CreditCardService {

    private static final CreditCardService INSTANCE = new CreditCardService();

    private CreditCardService() {
    }

    static CreditCardService getInstance() {
        return INSTANCE;
    }

    List<CreditCard> getByUser(User user) {
        return CreditCardDao.getInstance().getByUser(user);
    }

    /**
     * user in admin service
     *
     * @param creditCard
     * @param user
     * @return boolean
     */
    boolean save(CreditCard creditCard, User user) {
        return CreditCardDao.getInstance().save(creditCard, user);
    }

    boolean blockCreditCard(CreditCard creditCard, User user, boolean state) {
        return CreditCardDao.getInstance().blockCreditCard(creditCard, user, state);
    }

    Optional<CreditCard> getById(int id) {
        return CreditCardDao.getInstance().getById(id);
    }

    Optional<CreditCard> getByNumber(String number) {
        return CreditCardDao.getInstance().getByNumber(number);
    }

    boolean deleteById(int id, User user) {
        return CreditCardDao.getInstance().deleteById(id, user);
    }

    boolean deleteByNumber(String number, User user) {
        return CreditCardDao.getInstance().deleteByNumber(number, user);
    }

    void updateBalance(String creditCardNumber, double updatedBalance, Connection connection) throws SQLException {
        CreditCardDao.getInstance().updateBalance(creditCardNumber, updatedBalance, connection);
    }

    /**
     * use in admin service
     * @return []CreditCard
     */
    List<CreditCard> getAll() {
        return CreditCardDao.getInstance().getAll();
    }
}
