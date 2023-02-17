package com.demo.springboot.restapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    private UserDetailsRepository repository;

    public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void run(String...args) throws Exception {
        repository.save(new UserDetails("Elijah", "Admin"));
        repository.save(new UserDetails("Caleb", "Admin"));
        repository.save(new UserDetails("Joel", "User"));

        //list all users
        //List<UserDetails> users = repository.findAll();
        List<UserDetails> users = repository.findByRole("Admin");

        users.forEach(user -> logger.info(user.toString()));
    }

}
