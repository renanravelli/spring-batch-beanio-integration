package br.com.renanravelli.batch.configuration.item;

import br.com.cabal.sippe.writer.CSBeanWriter;
import br.com.renanravelli.batch.mapping.user.UserBody;
import br.com.renanravelli.batch.mapping.user.UserHeader;
import br.com.renanravelli.batch.mapping.user.UserRegistry;
import br.com.renanravelli.batch.model.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.List;

@Component("userItemWriter")
public class UserItemWriter implements ItemWriter<User> {

    @Value("${file.directory.out}")
    private String path;
    private UserRegistry userRegistry;
    private CSBeanWriter csBeanWriter;

    @PostConstruct
    public void initFile() {
        csBeanWriter = new CSBeanWriter
                .CSBeanWriterBuilder()
                .name("test.txt")
                .path(path)
                .record(UserHeader.class)
                .record(UserBody.class)
                .build();
    }

    @Override
    public void write(List<? extends User> list) throws Exception {
        userRegistry = new UserRegistry
                .UserRegistryBuilder()
                .build();

        createHeader();
        createBody((List<User>) list);

        csBeanWriter.escrever(userRegistry.getUsers());
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

    @PreDestroy
    public void destroy() {
        csBeanWriter.fechar();
    }
}
