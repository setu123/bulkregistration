package com.mt.bulkregistration;

import com.mt.bulkregistration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserItemProcessor implements ItemProcessor<User, User> {

    private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);
    Set<String> validUserMsisdn = new HashSet<>();
    @Autowired
    Validator<User> validator;

    private final String outputDir;
    private final String fileExtension;
    private final String greetingMale;
    private final String greetingFemale;

    public UserItemProcessor(String outputDir, String fileExtension, String greetingMale, String greetingFemale) throws IOException {
        this.outputDir = outputDir;
        this.fileExtension = fileExtension;
        this.greetingMale = greetingMale;
        this.greetingFemale = greetingFemale;

        Files.createDirectories(Paths.get(outputDir));
        Arrays.stream(Objects.requireNonNull(new File(outputDir).listFiles()))
                .forEach(File::delete);
    }

    @Override
    @Nullable
    public User process(User user) throws Exception {
        try {
            validator.validate(user);
        }
        catch (ValidationException e) {
            log.warn("Failed:: " + e.getMessage());
            return null;
        }

        if(validUserMsisdn.contains(user.getMsisdn())){
            log.warn("MSISDN " + user.getMsisdn() + " already exists");
            return null;
        }

        saveToFile(user);
        sendSMS(user);
        validUserMsisdn.add(user.getMsisdn());
        log.info("Success:: User with MSISDN " + user.getMsisdn() + " registered successfully");
        return user;
    }

    private void saveToFile(User user) throws IOException {
        Path path = Paths.get(outputDir + "/" + user.getMsisdn() + fileExtension);
        Files.write(path, user.toString().getBytes());
    }

    private void sendSMS(User user){
        switch (user.getGender()){
            case F: log.info(String.format(greetingFemale, user.getName()));
                break;
            case M: log.info(String.format(greetingMale, user.getName()));
                break;
            default:
                break;
        }
    }
}
