package br.com.renanravelli.batch.configuration.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowSample {

    @Bean("flow")
    public Flow flow1(@Qualifier("stepReaderUsers") Step stepReaderUsers,
                      @Qualifier("stepReaderFileUsers") Step userStepReaderFile,
                      @Qualifier("stepWriterUsersDB") Step stepWriterUsersBD) {
        return new FlowBuilder<SimpleFlow>("flow")
                .start(stepReaderUsers)
                .next(userStepReaderFile)
                .next(stepWriterUsersBD)
                .build();
    }
}
