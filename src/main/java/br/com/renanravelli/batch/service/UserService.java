package br.com.renanravelli.batch.service;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author renanravelli
 * @since 02/03/2019
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Reponsavel por buscar todos os usuarios da base de dados.
     * @return Todos os usuarios.
     */
    public List<User> findByAll() {
        return this.userRepository.findAll();
    }
}
