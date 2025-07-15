package com.hihi.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hihi.project.entity.DailyStatusEntity;

import java.time.LocalDate;

@Repository
public interface CheckRepository extends JpaRepository<DailyStatusEntity, Integer> {

    DailyStatusEntity findByUpdDate(LocalDate updDate);

}