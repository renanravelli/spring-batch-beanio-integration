package br.com.renanravelli.batch.mapping.user;

import br.com.renanravelli.batch.mapping.Registry;
import org.beanio.annotation.Field;
import org.beanio.annotation.Fields;
import org.beanio.annotation.Record;

import java.util.Date;

/**
 * @author renanravelli
 * @since 02/03/2019
 * Classe responsavel pelo mapeamento do body no arquivo.
 */
@Record
@Fields({@Field(length = 8, name = "identificador de registro", rid = true, literal = "REGISTER")})
public class UserBody implements Registry {

    @Field(length = 20, name = "nome do usuario", required = true)
    private String name;
    @Field(length = 20, name = "sobrenome do usuario", required = true)
    private String lastname;
    @Field(length = 8, name = "data de nascimento do usuario", format = "ddMMyyyy", required = true)
    private Date birthday;

    private UserBody(String name, String lastname, Date birthday) {
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
    }

    public static class UserBodyBuilder {
        private String name;
        private String lastname;
        private Date birthday;

        public UserBodyBuilder() {
        }

        public UserBodyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBodyBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserBodyBuilder birthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserBody build() {
            return new UserBody(name, lastname, birthday);
        }
    }
}
