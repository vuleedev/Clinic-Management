package com.hamster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hamster.model.TimeSlot;
import com.hamster.service.TimeSlotService;

@RestController
@RequestMapping("/rest/timeSlot")
public class TimeSlotController {

	@Autowired
	private TimeSlotService timeSlotService;

	@GetMapping
	public List<TimeSlot> getAllTimeSlot() {
        return timeSlotService.findAll();
    }

    @GetMapping("/{id}")
    public TimeSlot getTimeSlotById(@PathVariable("id") Long id) {
        return timeSlotService.findById(id);
    }

    @PostMapping
    public TimeSlot createTimeSlot(@RequestBody TimeSlot timeSlot) {
        return timeSlotService.create(timeSlot);
    }

    @PutMapping("/{id}")
    public TimeSlot updateTimeSlot(@PathVariable("id") Long id, @RequestBody TimeSlot timeSlot) {
    	timeSlot.setId(id);
        return timeSlotService.update(timeSlot);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeSlot(@PathVariable("id") Long id) {
        timeSlotService.delete(id);
    }
}
