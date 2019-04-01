package model.dto;

import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private Long id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private boolean administrator;
}
