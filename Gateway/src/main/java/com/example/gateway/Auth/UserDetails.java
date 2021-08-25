package com.example.gateway.Auth;

import com.project.luxuryCoupons.enums.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails {
    private String email;
    private String password;
    private ClientType clientType;
    private int userId;

    public UserDetails(String email, ClientType clientType, int userId) {
        this.email = email;
        this.clientType = clientType;
        this.userId = userId;
    }
}
