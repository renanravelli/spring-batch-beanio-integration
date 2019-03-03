package br.com.renanravelli.batch.service;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findByAll() {
        return this.userRepository.findAll();
    }
}
