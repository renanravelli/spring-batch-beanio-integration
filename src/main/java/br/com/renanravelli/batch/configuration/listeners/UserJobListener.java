package br.com.renanravelli.batch.configuration.listeners;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.service.UserService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class UserJobListener extends JobExecutionListenerSupport {

    private UserService userService;

    @Autowired
    public UserJobListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        createUsers().forEach(this.userService::save);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);
    }

    private List<User> createUsers() {
        User user01 = new User();
        user01.setName("Renan Ravelli");
        user01.setLastname("Alves de Araujo");
        user01.setBirthday(new Date());

        User user02 = new User();
        user02.setName("Usuário2 Teste");
        user02.setLastname("Teste do Teste");
        user02.setBirthday(new Date());

        User user03 = new User();
        user03.setName("Usuário3 Teste");
        user03.setLastname("Teste do Teste");
        user03.setBirthday(new Date());

        return Arrays.asList(user01, user02, user03);
    }
}
