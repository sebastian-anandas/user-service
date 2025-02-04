package io.seb.userservice.services;

import io.seb.userservice.models.Token;
import io.seb.userservice.models.User;
import io.seb.userservice.repositories.TokenRepository;
import io.seb.userservice.repositories.UserRepository;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token login(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = null;

        if (optionalUser.isEmpty()) {
            // return to signup page
        }
        user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            return null;
        }

        // generate the token
        Token token = createToken(user);

        return token;

    }

    public Token createToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        LocalDate thrityDaysFromToday = LocalDate.now().plusDays(30); // Example: 30 days from today
        Date expiryAt = Date.from(thrityDaysFromToday.atStartOfDay(ZoneId.systemDefault()).toInstant());

        token.setExpiryAt(expiryAt);

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }

    @Override
    public User signUp(String name, String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = null;

        if (optionalUser.isPresent()) {
            // navigate them to Login page

        }

        // create a new user object
        user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public User validateToken(String token) {

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThanEqual(
                token,
                false,
                new Date()
        );

        if(optionalToken.isEmpty()) {
            // throw some error
            return null;
        }

        return optionalToken.get().getUser();
    }

    @Override
    public void logout(String token) {

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(token, false);

        if(optionalToken.isEmpty()) {
            // throw some exception
        }

        Token token1 = optionalToken.get();
        token1.setDeleted(true);

        tokenRepository.save(token1);

    }
}
