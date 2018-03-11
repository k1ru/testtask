package com.arrival.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthTestData {
    private String description;
    private String url;
    private String userName;
    private String password;
    private int expectedStatusCode;
    private String expectedTitle;
    private String expectedUserName;
    private String expectedUserEmail;
    private boolean exitButtonDisplayed;
}
