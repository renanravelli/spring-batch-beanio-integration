package br.com.renanravelli.batch.streams.configuration;

import br.com.renanravelli.batch.streams.enums.StreamName;
import br.com.renanravelli.batch.streams.mapping.user.UserRegistry;
import org.beanio.StreamFactory;
import org.beanio.builder.CsvParserBuilder;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.FixedLengthParserBuilder;
import org.beanio.builder.StreamBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FileStreamFactory {


    @Bean
    public StreamFactory streamFactoryFile() {
        StreamFactory factory = StreamFactory.newInstance();


        // Definindo os streams factorys para o mapeamento usuario.
        StreamBuilder builderFixedLength = new StreamBuilder(StreamName.USER_FIXED_LENGTH.getStream());
        StreamBuilder builderDelimited = new StreamBuilder(StreamName.USER_DELIMITED.getStream());
        StreamBuilder builderCsv = new StreamBuilder(StreamName.USER_CSV.getStream());


        //Formatos possiveis
        builderFixedLength.format("fixedlength")
                .parser(new FixedLengthParserBuilder())
                .addRecord(UserRegistry.class)
                .build();

        builderDelimited.format("delimited")
                .parser(new DelimitedParserBuilder('|'))
                .addRecord(UserRegistry.class)
                .build();

        builderCsv.format("csv")
                .parser(new CsvParserBuilder())
                .addRecord(UserRegistry.class)
                .build();


        factory.define(builderFixedLength);
        factory.define(builderDelimited);
        factory.define(builderCsv);

        return factory;
    }
}
