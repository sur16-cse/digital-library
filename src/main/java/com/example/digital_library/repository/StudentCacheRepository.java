package com.example.digital_library.repository;

import com.example.digital_library.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class StudentCacheRepository {

    private static final String STUDENT_KEY_PREFIX = "std::";
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void set(Student student) {
        if (student.getId() == 0) {
            return;
        }

        String key = STUDENT_KEY_PREFIX + student.getId();
        redisTemplate.opsForValue().set(key, student, 3600, TimeUnit.SECONDS);
    }

    public Student get(int studentId) {
        if (studentId == 0) {
            return null;
        }

        String key = STUDENT_KEY_PREFIX + studentId;
        return (Student) redisTemplate.opsForValue().get(key);
    }
}
