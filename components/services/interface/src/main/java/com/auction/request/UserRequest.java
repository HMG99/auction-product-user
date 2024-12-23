package com.auction.request;

import com.auction.enums.Role;
import com.auction.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    public static final String USER_NAME_REGEX = "[A-Z][a-z]+";
    public static final String USER_NAME_REGEX_MSG = "User name permitted characters: A_Z, a-z";
    public static final String USER_NAME_NULL_MSG = "User name can not be null or empty";

    public static final String USER_SURNAME_REGEX = "[A-Z][a-z]+";
    public static final String USER_SURNAME_REGEX_MSG = "User surname permitted characters: A_Z, a-z";
    public static final String USER_SURNAME_NULL_MSG = "User surname can not be null or empty";


    public static final String USER_EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String USER_EMAIL_REGEX_MSG = "Wrong format of email";
    public static final String USER_EMAIL_NULL_MSG = "User email can not be null or empty";

    public static final String USER_YEAR_REGEX_MSG = "User year must be between 1950 and 2020";

    @Hidden
    private UUID id;
    @NotEmpty(message = USER_NAME_NULL_MSG)
    @Pattern(regexp = USER_NAME_REGEX, message = USER_NAME_REGEX_MSG)
    @Schema(example = "Robert", description = "The name of the user")
    private String name;
    @NotEmpty(message = USER_SURNAME_NULL_MSG)
    @Pattern(regexp = USER_SURNAME_REGEX, message = USER_SURNAME_REGEX_MSG)
    @Schema(example = "Evans", description = "The surname of the user")
    private String surname;
    @Schema(example = "1995", description = "The year of the user")
    private Integer year;
    @NotEmpty(message = USER_EMAIL_NULL_MSG)
    @Pattern(regexp = USER_EMAIL_REGEX, message = USER_EMAIL_REGEX_MSG)
    @Schema(example = "Robert@gmail.com", description = "The email of the user")
    private String email;
    @Schema(example = "password_123", description = "The password of the user")
    private String password;
    @Hidden
    private Role role;
    @Hidden
    private Status status;
    @Hidden
    private String verifyCode;
    @Hidden
    private String resetToken;
}
