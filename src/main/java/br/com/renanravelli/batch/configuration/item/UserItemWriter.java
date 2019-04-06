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

    @Override
    public void write(List<? extends User> list) throws Exception {

        UserRegistry.UserRegistryBuilder userRegistry = new UserRegistry
                .UserRegistryBuilder();


        list.forEach(o -> {
            try {
                userRegistry.body(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        UserRegistry userBuild = userRegistry.build();
        userRegistry.header(new UserHeader
                .UserHeaderBuilder()
                .dateGenerate(new Date())
                .registryAmount(
                        userBuild.getUsers().size()
                )
                .build());


        ItemUtils.writer(
                "userStream",
                path,
                "users.txt",
                userBuild.getUsers(),
                UserHeader.class,
                UserBody.class);
    }
}
