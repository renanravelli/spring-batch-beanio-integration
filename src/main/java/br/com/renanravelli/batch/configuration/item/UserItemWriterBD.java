package br.com.renanravelli.batch.configuration.item;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
@RequiredArgsConstructor
public class UserItemWriterBD {

    @Autowired
    EntityManagerFactory emf;

    @Bean("jpaUserItemWriter")
    public JpaItemWriter writer() {
        JpaItemWriter writer = new JpaItemWriter();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

}
