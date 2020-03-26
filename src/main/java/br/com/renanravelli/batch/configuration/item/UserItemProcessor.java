package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.streams.mapping.user.UserRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserItemProcessor implements ItemProcessor<User, UserRegistry> {

    @Override
    public UserRegistry process(User user) {
        Date birthday = Date.from(user.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return UserRegistry.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .birthday(birthday)
                .build();
    }
}
