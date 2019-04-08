package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.mapping.user.UserBody;
import br.com.renanravelli.batch.mapping.user.UserHeader;
import br.com.renanravelli.batch.mapping.user.UserRegistry;
import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.util.ItemUtils;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("userItemWriter")
public class UserItemWriter implements ItemWriter<User> {

    @Value("${file.directory.out}")
    private String path;
    private UserRegistry userRegistry;

    @Override
    public void write(List<? extends User> list) throws Exception {
        userRegistry = new UserRegistry.UserRegistryBuilder()
                .build();

        createBody((List<User>) list);
        createHeader();


        ItemUtils.writer(
                "userStream",
                path,
                "users.txt",
                userRegistry.getUsers(),
                UserHeader.class,
                UserBody.class);
    }

    private void createBody(List<User> users) throws Exception {
        this.userRegistry.getUserRegistryBuilder()
                .body(users);
    }

    private void createHeader() {
        userRegistry.getUserRegistryBuilder()
                .header(new UserHeader
                        .UserHeaderBuilder()
                        .dateGenerate(new Date())
                        .registryAmount(
                                userRegistry.getUsers().size()
                        ).build());
    }
}
