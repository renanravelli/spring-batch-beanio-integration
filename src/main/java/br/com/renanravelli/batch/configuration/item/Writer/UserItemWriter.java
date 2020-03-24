package br.com.renanravelli.batch.configuration.item.Writer;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.streams.configuration.FlatFileConfiguration;
import br.com.renanravelli.batch.streams.enums.FlatFileOption;
import br.com.renanravelli.batch.streams.enums.StreamName;
import br.com.renanravelli.batch.streams.mapping.user.UserHeader;
import br.com.renanravelli.batch.streams.mapping.user.UserRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserItemWriter extends StepExecutionListenerSupport implements ItemWriter<User> {

    @NonNull
    private FlatFileConfiguration configuration;
    @Value("${file.directory.out}")
    private String path;
    private UserRegistry userRegistry;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.configuration.initialize(FlatFileOption.WRITER, StreamName.USER_CSV, path);
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
