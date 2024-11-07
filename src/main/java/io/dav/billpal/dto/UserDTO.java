package io.dav.billpal.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Dave AKN
 * @version 1.0
 */

@Data
public class UserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phone;

    private String title;

    private String bio;

    private String imageUrl;

    private boolean enabled, isNotLocked, isUsingMfa;

    private LocalDateTime createdAt;
}
