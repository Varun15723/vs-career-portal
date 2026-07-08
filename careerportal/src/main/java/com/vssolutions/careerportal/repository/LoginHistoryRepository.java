package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    List<LoginHistory> findByEmailOrderByLoginTimeDesc(String email);
    List<LoginHistory> findAllByOrderByLoginTimeDesc();
}
