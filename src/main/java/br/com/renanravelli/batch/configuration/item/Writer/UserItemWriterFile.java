package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.streams.configuration.FlatFileConfiguration;
import br.com.renanravelli.batch.streams.enums.FlatFileOption;
import br.com.renanravelli.batch.streams.enums.StreamName;
import br.com.renanravelli.batch.streams.mapping.Registry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserItemWriterFile extends StepExecutionListenerSupport implements ItemWriter<Registry> {

    @NonNull
    private FlatFileConfiguration<Registry> configuration;
    @Value("${file.directory.out}")
    private String path;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.configuration.initialize(FlatFileOption.WRITER, StreamName.USER_DELIMITED, path);
    }

    @Override
    public void write(List<? extends Registry> items) throws Exception {
        this.configuration.getWriter().write(items);
    }
}
