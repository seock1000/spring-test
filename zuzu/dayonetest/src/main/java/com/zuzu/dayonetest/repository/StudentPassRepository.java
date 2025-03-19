package com.zuzu.dayonetest.repository;

import com.zuzu.dayonetest.model.StudentPass;
import com.zuzu.dayonetest.model.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPassRepository extends JpaRepository<StudentPass, Long> {
}
