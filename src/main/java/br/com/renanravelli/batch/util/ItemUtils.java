package br.com.renanravelli.batch.util;

import br.com.renanravelli.batch.mapping.Registry;
import org.beanio.StreamFactory;
import org.beanio.builder.FixedLengthParserBuilder;
import org.beanio.builder.StreamBuilder;
import org.beanio.spring.BeanIOFlatFileItemWriter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author renanravelli
 * @since 02/03/2019
 * Classe responsavel por ter todos os metodos necessarios implementados para realizar
 * a leitura ou escrita de um arquivo utilizando beanio.
 */
public class ItemUtils {

    /**
     * Realizada a escrita no arquivo.
     *
     * @throws Exception
     */
    public static org.springframework.batch.item.ItemWriter writer(String stream,
                                                                   String path,
                                                                   String fileName,
                                                                   List<Registry> registries,
                                                                   Class... classes) throws Exception {
        StreamBuilder streamBuilder = new StreamBuilder(stream);
        streamBuilder.format("fixedlength")
                .parser(new FixedLengthParserBuilder());
        Arrays.stream(classes).forEach(streamBuilder::addRecord);
        streamBuilder.build();

        StreamFactory streamFactory = StreamFactory.newInstance();
        streamFactory.define(streamBuilder);

        BeanIOFlatFileItemWriter writerBuilder = new BeanIOFlatFileItemWriter();
        writerBuilder.setStreamName(stream);
        writerBuilder.setTransactional(false);
        writerBuilder.setStreamFactory(streamFactory);
        writerBuilder.setResource(new FileSystemResource(path + File.separator + fileName));
        writerBuilder.open(new ExecutionContext());
        writerBuilder.afterPropertiesSet();
        writerBuilder.write(registries);
        return writerBuilder;
    }
}
