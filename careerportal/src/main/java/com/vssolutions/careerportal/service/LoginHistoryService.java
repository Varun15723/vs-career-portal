package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.LoginHistory;
import com.vssolutions.careerportal.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    public void record(String email, String userType, boolean success, String failureReason) {
        LoginHistory entry = new LoginHistory();
        entry.setEmail(email);
        entry.setUserType(userType);
        entry.setSuccess(success);
        entry.setFailureReason(failureReason);
        loginHistoryRepository.save(entry);
    }

    public List<LoginHistory> getHistoryForUser(String email) {
        return loginHistoryRepository.findByEmailOrderByLoginTimeDesc(email);
    }

    public List<LoginHistory> getAllHistory() {
        return loginHistoryRepository.findAllByOrderByLoginTimeDesc();
    }
}
