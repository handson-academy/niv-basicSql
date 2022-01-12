package com.handson.basic.repo;


import com.handson.basic.models.Student;
import com.handson.basic.models.StudentGrade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentGradeRepository extends CrudRepository<StudentGrade,Long> {

}
