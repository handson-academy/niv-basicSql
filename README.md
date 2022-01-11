# basicSql

https://git-scm.com/downloads
open user on github
https://github.com/settings/keys
clone me:
git clone https://github.com/handson-academy/basicSql.git

https://www.docker.com/products/docker-desktop
https://www.jetbrains.com/idea/download/#section=mac
https://tableplus.com/download

-- git + shelf

https://start.spring.io/  -> spring boot - 2.5.2

-- hello world

@RestController
@RequestMapping("/api/students")
public class StudentsController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> hello()
    {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

}


--- swagger

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.6.1</version>
		</dependency><!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.6.1</version>
		</dependency>




----
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}

http://localhost:8080/swagger-ui.html#

----- START DOCKER

docker run -d -p 5432:5432 -v postgresdata:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres postgres
docker ps
docker logs [containerid]


version: "3"
services:
  db:
    image: postgres
    environment:
      POSTGRES_PASSWORD: postgres
    ports:
    - 5432:5432
    volumes:
      - ./postgresdata:/var/lib/postgresql/data
    privileged: true

docker-compose up -d

-- Spring DATA

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>6.1.5.Final</version>
		</dependency>

		<dependency>
			<groupId>joda-time</groupId>
			<artifactId>joda-time</artifactId>
			<version>2.10.13</version>
		</dependency>



application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

#JPA properties
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

import org.joda.time.*;
import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Dates {
public static SimpleDateFormat shortDate = new SimpleDateFormat("YYYY-MM-dd");
public static TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Jerusalem");

    public Dates() {
    }

    public static String dateToStr(@Nullable LocalDate date) {
        return date == null ? null : shortDate.format(date);
    }

    public static Date atUtc(LocalDateTime date) {
        return atUtc(date, TIME_ZONE);
    }

    public static Date atUtc(LocalDateTime date, TimeZone zone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTimeZone(zone);
        calendar.set(date.getYear(), date.getMonthOfYear()-1, date.getDayOfMonth());//convert from locatDateTime to Calender time
        calendar.set(Calendar.HOUR_OF_DAY, date.getHourOfDay());
        calendar.set(Calendar.MINUTE, date.getMinuteOfHour());
        calendar.set(Calendar.SECOND, date.getSecondOfMinute());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date atUtc(@Nullable LocalDate date) {
        return atUtc(date, TIME_ZONE);
    }

    public static Date atUtc(@Nullable LocalDate date, TimeZone zone) {
        return date == null ? null : atUtc(date.toLocalDateTime(LocalTime.MIDNIGHT), zone);
    }

    public static LocalDateTime atLocalTime(Date date) {
        return atLocalTime(date, TIME_ZONE);
    }

    public static LocalDateTime atLocalTime(Date date, TimeZone zone) {
        var localDate = OffsetDateTime.ofInstant(date.toInstant(), zone.toZoneId()).toLocalDateTime();
        Calendar c = Calendar.getInstance();
        c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        c.set(Calendar.HOUR_OF_DAY, localDate.getHour());
        c.set(Calendar.MINUTE, localDate.getMinute());
        c.set(Calendar.SECOND, localDate.getSecond());
        c.set(Calendar.MILLISECOND, 0);
        LocalDateTime res = LocalDateTime.fromCalendarFields(c);
        return res;
    }

    public static Date nowUTC() {
        return DateTime.now().withZone(DateTimeZone.UTC).toDate();
    }

    public static String getFullDateTime() {
        return DateTime.now().withZone(DateTimeZone.UTC).toDateTimeISO().toString();
    }

    public static boolean equals(@Nullable Date date1, @Nullable Date date2) {
        if (date1 != null && date2 != null) {
            return date1.getTime() == date2.getTime();
        } else {
            return Objects.equals(date1, date2);
        }
    }
}

--- student.java

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
}
explain builder plugin

-- repository and service

		<dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-joda</artifactId>
			<version>2.12.3</version>
		</dependency>


public interface StudentRepository extends CrudRepository<Student,Long> {
}

@Service
public class StudentService {

    @Autowired
    StudentRepository repository;

    public Iterable<Student> all() {
        return repository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }


    public Student save(Student student) {
        return repository.save(student);
    }

    public void delete(Student student) {
        repository.delete(student);
    }

}

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
}


Student.java
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdAt")
    public LocalDateTime calcCreatedAt() {
        return Dates.atLocalTime(createdAt);
    }

StudentsController.java
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudents()
    {
        return new ResponseEntity<>(studentService.all(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneStudent(@PathVariable Long id)
    {
        return new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> insertStudent(@RequestBody StudentIn studentIn)
    {
        Student student = studentIn.toStudent();
        student = studentService.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentIn student)
    {
        Optional<Student> dbStudent = studentService.findById(id);
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        student.updateStudent(dbStudent.get());
        Student updatedStudent = studentService.save(dbStudent.get());
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable Long id)
    {
        Optional<Student> dbStudent = studentService.findById(id);
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        studentService.delete(dbStudent.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }

