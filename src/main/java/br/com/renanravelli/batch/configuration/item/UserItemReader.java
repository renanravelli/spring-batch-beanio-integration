package br.com.renanravelli.batch.configuration.item;

import br.com.renanravelli.batch.model.User;
import br.com.renanravelli.batch.service.UserService;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserItemReader extends StepExecutionListenerSupport implements ItemReader<User> {
    @Autowired
    private UserService userService;
    private List<User> users;
    private Integer amountUsers;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        super.beforeStep(stepExecution);
        users = this.userService.findByAll();
        amountUsers = users.size();
    }

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (amountUsers > 0) {
            do {
                User user = users.get(amountUsers - 1);
                amountUsers--;
                return user;
            } while (amountUsers > 0);
        }
        return null;
    }
}
