package com.hamter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamter.service.StatisticService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	
	@Autowired
    private StatisticService statisticService;

    @GetMapping("/status")
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    public List<Map<String, Object>> getStatisticsByStatus() {
        return statisticService.getStatisticsByStatus();
    }

    @GetMapping("/daily")
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    public List<Map<String, Object>> getStatisticsByDay() {
        return statisticService.getStatisticsByDay();
    }

    @GetMapping("/monthly")
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    public List<Map<String, Object>> getStatisticsByMonth() {
        return statisticService.getStatisticsByMonth();
    }

    @GetMapping("/yearly")
    @PreAuthorize("hasAnyAuthority('STAFF', 'MANAGE', 'CUST')")
    public List<Map<String, Object>> getStatisticsByYear() {
        return statisticService.getStatisticsByYear();
    }
}
