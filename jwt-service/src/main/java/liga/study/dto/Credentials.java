package liga.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Данные аутентификации/авторизации
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credentials {

    private String username;

    private String password;

}
