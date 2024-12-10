package com.zanchuyn.inventorymanagement.dtos.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginRequest {
    private String username;
    private String password;
}
