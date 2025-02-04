package io.seb.userservice.services;

import io.seb.userservice.models.Token;
import io.seb.userservice.models.User;

public interface UserService {

    Token login(String email, String password);

    User signUp(String name, String email, String password);

    User validateToken(String token);

    void logout(String token);

}
