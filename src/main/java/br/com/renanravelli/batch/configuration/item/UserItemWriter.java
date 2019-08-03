package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.streams.configuration.FlatFileConfiguration;
import br.com.renanravelli.batch.streams.enums.FlatFileOptionEnum;
import br.com.renanravelli.batch.streams.enums.StreamNameEnum;
import br.com.renanravelli.batch.streams.mapping.user.UserHeader;
import br.com.renanravelli.batch.streams.mapping.user.UserRegistry;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class UserItemWriter extends StepExecutionListenerSupport implements ItemWriter<User> {

    @Value("${file.directory.out}")
    private String path;
    private UserRegistry userRegistry;
    @Autowired
    private FlatFileConfiguration configuration;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.configuration.initialize(FlatFileOptionEnum.WRITER, StreamNameEnum.USER_CSV, path);
    }

    @Override
    public void write(List<? extends User> list) throws Exception {

        userRegistry = new UserRegistry
                .UserRegistryBuilder()
                .header(new UserHeader
                        .UserHeaderBuilder()
                        .dateGenerate(new Date())
                        .registryAmount(
                                list.size()
                        ).build())
                .body((List<User>) list)
                .build();

        this.configuration.getWriter().write(this.userRegistry.getUsers());

        this.configuration.close();
    }

}
