package com.github.agiledon.sisyphus.assist.printer;

import com.github.agiledon.sisyphus.domain.json.User;

public class UserDataFixture {
    protected User createUser() {
        User user = new User();
        user.setGender(User.Gender.MALE);
        User.Name name = new User.Name();
        name.setFirst("Yi");
        name.setLast("Zhang");
        user.setName(name);
        user.setVerified(true);
        user.setUserImage("00001111".getBytes());
        return user;
    }
}
