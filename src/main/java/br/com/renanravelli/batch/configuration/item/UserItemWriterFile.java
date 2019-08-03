package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.streams.configuration.FlatFileConfiguration;
import br.com.renanravelli.batch.streams.enums.FlatFileOptionEnum;
import br.com.renanravelli.batch.streams.enums.StreamNameEnum;
import br.com.renanravelli.batch.streams.mapping.Registry;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserItemWriterFile extends StepExecutionListenerSupport implements ItemWriter<Registry> {

    @Autowired
    private FlatFileConfiguration<Registry> configuration;
    @Value("${file.directory.out}")
    private String path;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.configuration.initialize(FlatFileOptionEnum.WRITER, StreamNameEnum.USER_DELIMITED, path);
    }

    @Override
    public void write(List<? extends Registry> items) throws Exception {
        this.configuration.getWriter().write(items);
    }
}
