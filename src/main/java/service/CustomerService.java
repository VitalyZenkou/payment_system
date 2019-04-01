package service;

import model.dto.Address;
import model.dto.CreditCard;
import model.dto.Payment;
import model.dto.User;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerService {

    private static final UserService userService = UserService.getInstance();
    private static final AddressService addressService = AddressService.getInstance();
    private static final CreditCardService creditCardService = CreditCardService.getInstance();
    private static final PaymentService paymentService = PaymentService.getInstance();
    private static final CustomerService INSTANCE = new CustomerService();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        return INSTANCE;
    }

    public Optional<User> getUserById(int id) {
        return userService.getById(id);
    }

    public Optional<User> login(String login, String password) {
        return userService.login(login, password);
    }

    public boolean saveUserFromRegistration(User user, Address address) {
        Connection connection = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            userService.save(user, connection);
            addressService.saveFromRegistration(user, address, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                return false;
            } catch (SQLException e1) {
                System.err.println("Can't savePayment user! \n" + e1.toString());
                return false;
            }
        }
        return true;
    }

    public List<Address> getAddressesByUser(User user) {
        return addressService.getByUser(user);
    }

    public boolean saveAddress(Address address, User user) {
        return addressService.save(address, user);
    }

    public Optional<Address> getAddressById(int id) {
        return addressService.getById(id);
    }

    public boolean deleteAddressById(int id) {
        return addressService.deleteById(id);
    }

    public List<CreditCard> getCreditCardsByUser(User user) {
        return creditCardService.getByUser(user);
    }

    public boolean blockCreditCard(CreditCard creditCard, User user) {
        return creditCardService.blockCreditCard(creditCard, user, true);
    }

    public Optional<CreditCard> getCreditCardById(int id) {
        return creditCardService.getById(id);
    }

    public Optional<CreditCard> getCreditCardByNumber(String number) {
        return creditCardService.getByNumber(number);
    }

    public boolean deleteCreditCardByNumber(String number, User user) {
        return creditCardService.deleteByNumber(number, user);
    }

    public boolean deleteCreditCardById(int id, User user) {
        return creditCardService.deleteById(id, user);
    }

    public List<Payment> getPaymentsByUser(User user) {
        return paymentService.getByUser(user);
    }

    public boolean savePayment(Payment payment, User user, CreditCard creditCard) {
        if (!creditCard.isBlocked()) {
            Connection connection = null;
            double updatedBalance = creditCard.getBalance() - payment.getCost();
            try {
                connection = ConnectionManager.get();
                connection.setAutoCommit(false);
                paymentService.savePayment(payment, user, connection);
                creditCardService.updateBalance(creditCard.getNumber(), updatedBalance, connection);
                connection.commit();
            } catch (SQLException e) {
                try {
                    if (connection != null) {
                        connection.rollback();
                        connection.close();
                    }
                    return false;
                } catch (SQLException e1) {
                    System.err.println(String.format("Can't save payment for user %s \n", user.getName()) + e1.toString());
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean saveTransaction(Payment payment, User user, CreditCard creditCardFrom, CreditCard creditCardTo) {
        if (!creditCardFrom.isBlocked()) {
            Connection connection = null;
            double updatedBalanceFrom = creditCardFrom.getBalance() - payment.getCost();
            double updatedBalanceTo = creditCardTo.getBalance() + payment.getCost();
            try {
                connection = ConnectionManager.get();
                connection.setAutoCommit(false);
                paymentService.saveTransaction(payment, user, connection);
                creditCardService.updateBalance(creditCardFrom.getNumber(), updatedBalanceFrom, connection);
                creditCardService.updateBalance(creditCardTo.getNumber(), updatedBalanceTo, connection);
                connection.commit();
            } catch (SQLException e) {
                try {
                    if (connection != null) {
                        connection.rollback();
                        connection.close();
                    }
                    return false;
                } catch (SQLException e1) {
                    System.err.println(String.format("Can't save transaction for user %s", user.getName()) + e1.toString());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean updateBalance(CreditCard creditCard, Payment payment) {
        double updatedBalance = creditCard.getBalance() - payment.getCost();
        Connection connection = null;
        try {
            connection = ConnectionManager.get();
            creditCardService.updateBalance(creditCard.getNumber(), updatedBalance, connection);
        } catch (SQLException e) {
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
