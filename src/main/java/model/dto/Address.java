package model.dto;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    private int id;
    private int userId;
    private String city;
    private String street;
    private String phoneNumber;
    private int houseNumber;
    private int flatNumber;
}
