package br.com.renanravelli.batch.repository;

import br.com.renanravelli.batch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author renanravelli
 * @since 02/03/2019
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
