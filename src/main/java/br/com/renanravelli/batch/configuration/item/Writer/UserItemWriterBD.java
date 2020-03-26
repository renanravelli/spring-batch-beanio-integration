package br.com.renanravelli.batch.configuration.item.Writer;

import br.com.renanravelli.batch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * @author joao4018
 * @since 20/03/2020
 */

@Component
@RequiredArgsConstructor
public class UserItemWriterBD {


    private final EntityManagerFactory emf;

    @Bean("jpaUserItemWriter")
    public JpaItemWriter<User> writer() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

}
