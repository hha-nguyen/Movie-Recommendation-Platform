package com.mesto.movieplatform.dtoes;

import lombok.*;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.mesto.movieplatform.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO {
    @NotEmpty(message = "Username must not be empty")
    private String username;
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private Integer age;
    private String profileImage;
}
