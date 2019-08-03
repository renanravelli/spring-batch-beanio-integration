package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.streams.configuration.FlatFileConfiguration;
import br.com.renanravelli.batch.streams.enums.FlatFileOptionEnum;
import br.com.renanravelli.batch.streams.enums.StreamNameEnum;
import br.com.renanravelli.batch.streams.mapping.Registry;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserItemReaderFile extends StepExecutionListenerSupport implements ItemReader<Registry> {

    @Autowired
    private FlatFileConfiguration<Registry> configuration;
    @Value("${file.directory.out}")
    private String path;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.configuration.initialize(FlatFileOptionEnum.READER, StreamNameEnum.USER_CSV, path);
    }

    @Override
    public Registry read() {
        try {
            return this.configuration.getReader().read();
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro na leitura do registro", e);
        }
    }
}
