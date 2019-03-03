package br.com.renanravelli.batch.mapping.user;

import br.com.renanravelli.batch.mapping.Registry;
import br.com.renanravelli.batch.model.User;
import org.springframework.batch.item.ItemReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author renanravelli
 * @since 02/03/2019
 * Classe responsavel por montar o arquivo, preenchendo o header e body com
 * base nos dados recuperados do banco de dados.
 */
public class UserRegistry implements Registry {

    private List<Registry> users = new ArrayList<>();

    private UserRegistry(List<Registry> users) {
        this.users = users;
    }

    public static class UserRegistryBuilder {
        private List<Registry> registries = new ArrayList<>();

        public UserRegistryBuilder() {
        }

        public UserRegistryBuilder header(UserHeader header) {
            registries.add(0, header);
            return this;
        }

        public UserRegistryBuilder body(ItemReader<User> body) throws Exception {
            User user = null;

            //ler ate que seja null.
            while ((user = body.read()) != null) {
                registries.add(new UserBody
                        .UserBodyBuilder()
                        .name(user.getName())
                        .lastname(user.getLastname())
                        .birthday(user.getBirthday())
                        .build()
                );
            }

            // se o header nao estiver preenchido.
            if (!(registries.get(0) instanceof UserHeader)) {
                registries.add(0, new UserHeader
                        .UserHeaderBuilder()
                        .dateGenerate(new Date())
                        .registryAmount(registries.size())
                        .build());
            }
            return this;
        }

        public UserRegistry build() {
            return new UserRegistry(registries);
        }
    }

    public List<Registry> getUsers() {
        return users;
    }
}
