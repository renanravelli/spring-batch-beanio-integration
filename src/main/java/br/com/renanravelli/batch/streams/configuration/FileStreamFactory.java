package br.com.renanravelli.batch.streams.configuration;

import br.com.renanravelli.batch.streams.enums.StreamNameEnum;
import br.com.renanravelli.batch.streams.mapping.user.UserBody;
import br.com.renanravelli.batch.streams.mapping.user.UserHeader;
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
    public StreamFactory streamFactory() {
        StreamFactory factory = StreamFactory.newInstance();


        // Definindo os streams factorys para o mapeamento usuario.
        StreamBuilder builderFixedLength = new StreamBuilder(StreamNameEnum.USER_FIXED_LENGTH.getStream());
        StreamBuilder builderDelimited = new StreamBuilder(StreamNameEnum.USER_DELIMITED.getStream());
        StreamBuilder builderCsv = new StreamBuilder(StreamNameEnum.USER_CSV.getStream());


        //Formatos possiveis
        builderFixedLength.format("fixedlength")
                .parser(new FixedLengthParserBuilder())
                .addRecord(UserHeader.class)
                .addRecord(UserBody.class)
                .build();

        builderDelimited.format("delimited")
                .parser(new DelimitedParserBuilder('|'))
                .addRecord(UserHeader.class)
                .addRecord(UserBody.class)
                .build();

        builderCsv.format("csv")
                .parser(new CsvParserBuilder())
                .addRecord(UserHeader.class)
                .addRecord(UserBody.class)
                .build();


        factory.define(builderFixedLength);
        factory.define(builderDelimited);
        factory.define(builderCsv);

        return factory;
    }
}
