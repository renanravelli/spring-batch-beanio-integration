package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.model.User;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@AllArgsConstructor
public class UserItemReader {

    private EntityManager entityManager;

    @Bean("jpaUserItemReader")
    public JpaPagingItemReader read() {
        return new JpaPagingItemReaderBuilder<User>()
                .name("UserItemReader")
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .queryString("SELECT u FROM User u")
                .pageSize(2)
                .transacted(false)
                .build();
    }
}
