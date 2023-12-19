package com.vanannek.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vanannek.entity.User;
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
    @JsonIgnore
    private String email;
    private User.EGender gender;
}
