package com.mt.bulkregistration.model;

import com.mt.bulkregistration.validator.EnumNamePattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public enum SIM_TYPE{PREPAID, POSTPAID}

    public enum GENDER{F, M}

    @Id
    @NotBlank(message = "Should not be empty")
    @Pattern(regexp = "^\\+(\\d{3,})$", message = "MSISDN should comply to country's standard (e.g. +66)")
    String msisdn;
    @NotNull(message = "Should not be empty")
    @EnumNamePattern(regexp = "POSTPAID|PREPAID", message = "SIM_TYPE can only be PREPAID or POSTPAID")
    SIM_TYPE simType;
    @NotBlank(message = "Should not be empty")
    @Pattern(regexp = "[a-zA-Z0-9 ]+", message = "Name shouldn't have any special character")
    String name;
    @NotNull(message = "Should not be empty")
    @Past(message = "DATE_OF_BIRTH shouldn't be TODAY or FUTURE")
    Date dateOfBirth;
    @NotNull(message = "Should not be empty")
    @EnumNamePattern(regexp = "M|F", message = "Gender can only be F or M")
    GENDER gender;
    @NotBlank(message = "Should not be empty")
    @Length(min = 20, message = "Address must at least be 20 characters long")
    String address;
    @NotBlank(message = "Should not be empty")
    @Pattern(regexp = "[0-9]+[a-zA-Z]|[a-zA-Z]+[0-9]", message = "ID_NUMBER should be a mix of characters & numbers")
    String idNumber;
}
