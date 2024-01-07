package com.vanannek.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vanannek.user.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class UserDTO {
    private String username;
    @JsonIgnore
    private String passHash;
    private String fullName;
    private String email;
    private User.EGender gender;
}
