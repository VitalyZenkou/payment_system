package model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment {

    private int id;
    private String creditCardNumber;
    private double cost;
    private String organization;
    private String toCreditCard;
    private boolean transaction;
    private int userId;
}
