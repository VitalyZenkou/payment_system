package model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditCard {

    private int id;
    private int userId;
    private String number;
    private String validityDate;
    private int pinCode;
    private int cvv;
    private String creditCardType;
    private double balance;
    private boolean blocked;
}
