package com.hihi.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hihi.project.dto.ApiResponse;
import com.hihi.project.entity.DailyStatusEntity;
import com.hihi.project.service.CheckService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/checkin")
@RequiredArgsConstructor
public class CheckController {

    final private CheckService checkService;

    @GetMapping("/today")
    public ApiResponse<Boolean> findTodayStatus() {
        DailyStatusEntity entity = checkService.findTodayStatus();
        boolean isChecked = entity != null && entity.isCheck();
        return ApiResponse.success(isChecked);
    }

    @PostMapping("/today")
    public ApiResponse<String> changeTodayStatus() {
        checkService.changeTodayStatus();
        return ApiResponse.success("打卡成功");
    }

}
