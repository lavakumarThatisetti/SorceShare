package com.sorceshare.userstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenMapper {
    private String access_token;
    private long id;
    private String userName;
    private String name;
}
