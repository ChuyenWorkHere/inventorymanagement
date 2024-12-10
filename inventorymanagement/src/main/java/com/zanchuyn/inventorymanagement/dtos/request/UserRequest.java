package com.zanchuyn.inventorymanagement.dtos.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String role;
}
