package com.enrollment.service;

import com.enrollment.model.Student;
import com.enrollment.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Student save(Student student) {
        studentRepository.findByEmail(student.getEmail()).ifPresent(existing -> {
            boolean isDifferentStudent = !existing.getId().equals(student.getId());
            if (isDifferentStudent) {
                throw new IllegalStateException("Email is already registered: " + student.getEmail());
            }
        });
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> search(String query) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }
}
