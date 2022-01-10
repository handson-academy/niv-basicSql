package com.handson.basic.models;

import com.handson.basic.util.Dates;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Date createdAt = Dates.nowUTC();

    @NotEmpty
    @Length(max = 60)
    private String fullname;

    private Date birthDate;

    @Min(100)
    @Max(800)
    private Integer satScore;

    private Double graduationScore;

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFullname() {
        return fullname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Integer getSatScore() {
        return satScore;
    }

    public Double getGraduationScore() {
        return graduationScore;
    }

    public static final class StudentBuilder {
        private Long id;
        private Date createdAt = Dates.nowUTC();
        private String fullname;
        private Date birthDate;
        private Integer satScore;
        private Double graduationScore;

        private StudentBuilder() {
        }

        public static StudentBuilder aStudent() {
            return new StudentBuilder();
        }

        public StudentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StudentBuilder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public StudentBuilder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public StudentBuilder satScore(Integer satScore) {
            this.satScore = satScore;
            return this;
        }

        public StudentBuilder graduationScore(Double graduationScore) {
            this.graduationScore = graduationScore;
            return this;
        }

        public Student build() {
            Student student = new Student();
            student.fullname = this.fullname;
            student.satScore = this.satScore;
            student.graduationScore = this.graduationScore;
            student.createdAt = this.createdAt;
            student.birthDate = this.birthDate;
            student.id = this.id;
            return student;
        }
    }
}