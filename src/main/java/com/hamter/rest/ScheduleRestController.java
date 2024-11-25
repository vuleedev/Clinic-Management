package com.hamter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hamter.model.Schedule;
import com.hamter.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleRestController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable("id") Long id) {
        return scheduleService.findById(id);
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.create(schedule);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable("id") Long id, @RequestBody Schedule schedule) {
    	schedule.setId(id);
        return scheduleService.update(schedule);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.delete(id);
    }
}
