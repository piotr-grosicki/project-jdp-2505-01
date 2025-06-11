package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.UserNotFoundByIdException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kodilla.ecommercee.exception.UserNotFoundByMailException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) throws UserNotFoundByIdException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
    }

    public User getUserByEmail(String email) throws UserNotFoundByMailException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundByMailException(email));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) throws UserNotFoundByIdException {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundByIdException(user.getId());
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) throws UserNotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User blockUser(Long id) throws UserNotFoundByIdException {
        User user = getUser(id);
        user.setBlocked(true);
        return userRepository.save(user);
    }

    @Transactional
    public User generateKey(Long id) throws UserNotFoundByIdException {
        User user = getUser(id);
        user.setToken(UUID.randomUUID().toString());
        user.setTokenCreatedAt(LocalDateTime.now());
        user.setTokenExpiresAt(LocalDateTime.now().plusHours(1));
        return userRepository.save(user);
    }
}
