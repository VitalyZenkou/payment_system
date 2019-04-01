package service;

import model.dto.CreditCard;
import model.dto.User;

import java.util.List;

public class AdminService {

    private static final AdminService INSTANCE = new AdminService();

    private AdminService() {
    }

    public static AdminService getInstance() {
        return INSTANCE;
    }

    public CustomerService getCustomerService() {
        return CustomerService.getInstance();
    }

    public List<User> getAllUsers() {
        return UserService.getInstance().getAll();
    }

    public boolean deleteUser(User user) {
        return UserService.getInstance().delete(user);
    }

    public boolean saveCreditCard(CreditCard creditCard, User user) {
        return CreditCardService.getInstance().save(creditCard, user);
    }

    public List<User> getUserWithoutCard() {
        return UserService.getInstance().getWithoutCard();
    }

    public List<CreditCard> getAllCreditCards() {
        return CreditCardService.getInstance().getAll();
    }

    public boolean unblockCreditCard(CreditCard creditCard, User user) {
        return CreditCardService.getInstance().blockCreditCard(creditCard, user, false);
    }
}
