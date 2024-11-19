package io.dav.billpal.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author Dave AKN
 * @version 1.0
 */

@Data
public class LoginRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
