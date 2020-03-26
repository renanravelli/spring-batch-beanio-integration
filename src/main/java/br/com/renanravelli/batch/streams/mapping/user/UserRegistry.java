package br.com.renanravelli.batch.streams.mapping.user;

import br.com.renanravelli.batch.streams.mapping.Registry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.beanio.annotation.Field;
import org.beanio.annotation.Fields;
import org.beanio.annotation.Record;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author renanravelli
 * @since 02/03/2019
 * Classe responsavel pelo mapeamento do body no arquivo.
 */
@Getter
@Record
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Fields({@Field(length = 8, name = "identificador de registro", rid = true, literal = "REGISTER")})
public class UserRegistry implements Registry {

    @Field(length = 20, name = "nome do usuario", required = true)
    private String name;
    @Field(length = 20, name = "sobrenome do usuario", required = true)
    private String lastname;
    @Field(length = 8, name = "data de nascimento do usuario", format = "ddMMyyyy", required = true)
    private Date birthday;
}
