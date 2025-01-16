package com.hamter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hamter.repository.BookingRepository;

@Service
public class StatisticService {

	@Autowired
    private BookingRepository bookingRepository;

    public List<Map<String, Object>> getStatisticsByStatus() {
        List<Object[]> data = bookingRepository.countByStatus();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] obj : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", obj[0]);
            map.put("count", obj[1]);
            result.add(map);
        }
        return result;
    }

    public List<Map<String, Object>> getStatisticsByDay() {
        List<Object[]> data = bookingRepository.countByDate();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] obj : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", obj[0]);
            map.put("count", obj[1]);
            result.add(map);
        }
        return result;
    }

    public List<Map<String, Object>> getStatisticsByMonth() {
        List<Object[]> data = bookingRepository.countByMonth();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] obj : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", obj[0]);
            map.put("count", obj[1]);
            result.add(map);
        }
        return result;
    }

    public List<Map<String, Object>> getStatisticsByYear() {
        List<Object[]> data = bookingRepository.countByYear();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] obj : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("year", obj[0]);
            map.put("count", obj[1]);
            result.add(map);
        }
        return result;
    }
}
