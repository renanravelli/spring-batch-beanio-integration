package br.com.renanravelli.batch.configuration.step;

import br.com.renanravelli.batch.mapping.user.UserBody;
import br.com.renanravelli.batch.mapping.user.UserHeader;
import br.com.renanravelli.batch.mapping.user.UserRegistry;
import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.service.UserService;
import br.com.renanravelli.batch.util.ItemUtils;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;

/**
 * @author renanravelli
 * @since 02/03/2019
 */
@Configuration
public class UserStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private UserService userService;
    @Value("${file.directory.out}")
    private String path;


    /**
     * Item responsavel por realizar a leitura dos dados na base de dados.
     */
    @Bean("reader")
    public ItemReader<User> reader() {
        Iterator<User> users = this.userService.findByAll().iterator();
        return () -> users.hasNext() ? users.next() : null;
    }

    /**
     * Item responsavel por realizar a escrita no arquivo utilizando o
     * mapeamento do beanio.
     */
    @Bean("writer")
    public ItemWriter writer() throws Exception {

        UserRegistry userRegistry = new UserRegistry
                .UserRegistryBuilder()
                .body(reader())
                .build();

        return ItemUtils.writer(
                "userStream",
                path,
                "users.txt",
                userRegistry.getUsers(),
                UserHeader.class, UserBody.class);
    }

    /**
     * Step responsavel por realizar a execucao dos itens reader e writer.
     */
    @Bean("stepReaderUsers")
    public Step stepReaderUsers(@Qualifier("reader") ItemReader reader, @Qualifier("writer") ItemWriter writer) {
        return this.stepBuilderFactory.get("STEP_READER_USERS_IN_DATABASE")
                .chunk(100)
                .reader(reader)
                .writer(writer)
                .build();
    }
}
