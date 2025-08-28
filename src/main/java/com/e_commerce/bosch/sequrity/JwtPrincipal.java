package com.e_commerce.bosch.sequrity;

import com.e_commerce.bosch.entities.Role;
import lombok.*;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JwtPrincipal implements Principal {

    private Long userId;
    private String username;
    private Role role;

    @Override
    public String getName() {
        return username;
    }
}
