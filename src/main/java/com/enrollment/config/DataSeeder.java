package com.enrollment.config;

import com.enrollment.model.Course;
import com.enrollment.model.Enrollment;
import com.enrollment.model.Student;
import com.enrollment.repository.CourseRepository;
import com.enrollment.repository.EnrollmentRepository;
import com.enrollment.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public DataSeeder(StudentRepository studentRepository,
                      CourseRepository courseRepository,
                      EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() > 0) return;

        List<Student> students = studentRepository.saveAll(List.of(
            new Student("Alice",   "Smith",    "alice.smith@uni.edu",    LocalDate.of(2001, 3, 15)),
            new Student("Bob",     "Jones",    "bob.jones@uni.edu",      LocalDate.of(2000, 7, 22)),
            new Student("Carol",   "White",    "carol.white@uni.edu",    LocalDate.of(2002, 1,  8)),
            new Student("David",   "Brown",    "david.brown@uni.edu",    LocalDate.of(2001, 11, 30)),
            new Student("Eve",     "Davis",    "eve.davis@uni.edu",      LocalDate.of(2003, 5, 19)),
            new Student("Frank",   "Miller",   "frank.miller@uni.edu",   LocalDate.of(2000, 9,  4)),
            new Student("Grace",   "Wilson",   "grace.wilson@uni.edu",   LocalDate.of(2002, 6, 27)),
            new Student("Henry",   "Moore",    "henry.moore@uni.edu",    LocalDate.of(2001, 2, 14)),
            new Student("Isla",    "Taylor",   "isla.taylor@uni.edu",    LocalDate.of(2003, 8,  3)),
            new Student("James",   "Anderson", "james.anderson@uni.edu", LocalDate.of(2000, 12, 20))
        ));

        List<Course> courses = courseRepository.saveAll(List.of(
            new Course("Introduction to Java",    "CS101",  "Basics of Java programming",               3),
            new Course("Data Structures",         "CS201",  "Arrays, lists, trees, graphs",             4),
            new Course("Database Systems",        "CS301",  "SQL, normalization, transactions",         3),
            new Course("Operating Systems",       "CS401",  "Processes, memory, file systems",          4),
            new Course("Web Development",         "WEB101", "HTML, CSS, JavaScript fundamentals",       3),
            new Course("Spring Framework",        "WEB201", "Spring Boot, MVC, JPA",                   4),
            new Course("Algorithms",              "CS202",  "Sorting, searching, complexity",           4),
            new Course("Software Engineering",    "SE301",  "SDLC, Agile, design patterns",            3),
            new Course("Machine Learning Basics", "AI101",  "Regression, classification, clustering",  3),
            new Course("Computer Networks",       "NET201", "TCP/IP, HTTP, DNS, security",              3)
        ));

        LocalDate today = LocalDate.now();
        enrollmentRepository.saveAll(List.of(
            new Enrollment(students.get(0), courses.get(0), today.minusDays(60), "A"),
            new Enrollment(students.get(0), courses.get(1), today.minusDays(55), "B+"),
            new Enrollment(students.get(1), courses.get(0), today.minusDays(50), "B"),
            new Enrollment(students.get(1), courses.get(2), today.minusDays(45), null),
            new Enrollment(students.get(2), courses.get(3), today.minusDays(40), "A-"),
            new Enrollment(students.get(3), courses.get(4), today.minusDays(35), "B+"),
            new Enrollment(students.get(4), courses.get(5), today.minusDays(30), null),
            new Enrollment(students.get(5), courses.get(6), today.minusDays(25), "A"),
            new Enrollment(students.get(6), courses.get(7), today.minusDays(20), "C+"),
            new Enrollment(students.get(7), courses.get(8), today.minusDays(15), null)
        ));
    }
}
