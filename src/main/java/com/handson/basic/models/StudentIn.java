package com.handson.basic.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handson.basic.util.Dates;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static com.handson.basic.models.Student.StudentBuilder.aStudent;


public class StudentIn implements Serializable {

    @Length(max = 60)
    private String fullname;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Min(100)
    @Max(800)
    private Integer satScore;

    private Double graduationScore;

    public Student toStudent() {
        return aStudent().birthDate(Dates.atUtc(birthDate)).fullname(fullname).satScore(satScore).graduationScore(graduationScore).build();
    }

    public void updateStudent(Student student) {
        student.setBirthDate(Dates.atUtc(birthDate));
        student.setFullname(fullname);
        student.setSatScore(satScore);
        student.setGraduationScore(graduationScore);
    }

    public String getFullname() {
        return fullname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getSatScore() {
        return satScore;
    }

    public Double getGraduationScore() {
        return graduationScore;
    }
}
