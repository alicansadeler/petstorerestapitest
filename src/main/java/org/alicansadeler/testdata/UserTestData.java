package org.alicansadeler.testdata;

import org.alicansadeler.pojos.UserTestPojo;

public class UserTestData {

    public static UserTestPojo getDefaultUser() {

        return UserTestPojo.builder()
                .username("defaultuser")
                .firstName("Ali")
                .lastName("Sadeler")
                .email("ali@test.com")
                .password("test123")
                .phone("1234567890")
                .userStatus(1)
                .build();
    }

    public static UserTestPojo getUserWithCustomUsername(String username) {
        return UserTestPojo.builder()
                .username(username)
                .firstName("Ali")
                .lastName("Sadeler")
                .email("ali")
                .password("test123")
                .phone("1234567890")
                .userStatus(1)
                .build();
    }
}
