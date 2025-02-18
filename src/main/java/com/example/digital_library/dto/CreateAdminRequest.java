package com.example.digital_library.dto;

import com.example.digital_library.model.Admin;
import com.example.digital_library.model.SecuredUser;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class CreateAdminRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Admin to() {
        return Admin.builder().
                name(name).
                securedUser(
                        SecuredUser.builder().
                                username(this.username)
                                .password(this.password)
                                .build()
                ).
                build();
    }
}
