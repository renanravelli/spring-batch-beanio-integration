package br.com.renanravelli.batch.configuration.item.Processor;

import br.com.renanravelli.batch.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) {
        final String firstName = user.getName().toUpperCase();
        final String lastName = user.getLastname().toUpperCase();

        final User transformed = new User(firstName, lastName, user.getBirthday());
        return transformed;
    }
}



