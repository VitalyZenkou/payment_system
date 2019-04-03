package util;

import lombok.SneakyThrows;

import java.util.Random;

public class CreditCardDataGenerator {

    private static final Random random = new Random();

    private CreditCardDataGenerator() {
    }

    public static int generatePinCode() {
        return 1000 + random.nextInt(9999 - 1000);
    }

    public static int generateCvv() {
        return 100 + random.nextInt(999 - 100);
    }

    @SneakyThrows
    public static String generateCreditCardNumber() {
        String creditCardNumber = "";
        for (int i = 0; i < 4; i++) {
            Thread.sleep(1000);
            if (i != 3) {
                creditCardNumber = creditCardNumber + generatePinCode() + " ";
            } else {
                creditCardNumber += generatePinCode();
            }
        }
        return creditCardNumber;
    }
}
