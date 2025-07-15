package com.hihi.project.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.hihi.project.dao.CheckRepository;
import com.hihi.project.entity.DailyStatusEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckService {

    final private CheckRepository checkRepository;

    public DailyStatusEntity findTodayStatus() {
        LocalDate today = LocalDate.now();
        return checkRepository.findByUpdDate(today);
    }

    public void changeTodayStatus() {
        LocalDate today = LocalDate.now();
        DailyStatusEntity entity = DailyStatusEntity.builder()
                .updDate(today)
                .isCheck(true)
                .build();

        checkRepository.save(entity);
    }

}
