package com.handson.basic.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.handson.basic.util.Dates;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;

@Entity
@SqlResultSetMapping(name = "StudentOut")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentOut {

    private Long id;

    private Date createdat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdat")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdat);
    }

    private String fullname;
    private Date birthdate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthdate")
    public LocalDateTime calcBirthDate() {
        return Dates.atLocalTime(birthdate);
    }

    private Integer satscore;
    private Double graduationscore;
    private Double avgscore;
    public Date getCreatedat() {
        return createdat;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public Integer getSatscore() {
        return satscore;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setBirthdate(Date birthDate) {
        this.birthdate = birthDate;
    }

    public void setSatscore(Integer satscore) {
        this.satscore = satscore;
    }

    public Double getGraduationscore() {
        return graduationscore;
    }

    public void setGraduationscore(Double graduationscore) {
        this.graduationscore = graduationscore;
    }

    public Double getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(Double avgscore) {
        this.avgscore = avgscore;
    }
}

