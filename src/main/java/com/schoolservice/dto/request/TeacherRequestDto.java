package com.schoolservice.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherRequestDto {

    @NotBlank(message = "User name shouldn't be blank")
    private String userName;

    @NotBlank(message = "First name shouldn't be blank")
    private String firstName;

    @NotBlank(message = "Last name shouldn't be blank")
    private String lastName;

    @Email(message = "Email hasn't valid format")
    private String email;

    @NotNull(message = "Date of birth shouldn't be null")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;
}
