package com.hamter.rest;

import com.hamter.model.TimeSlot;
import com.hamter.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
public class TimeSlotRestController {

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping
    public List<TimeSlot> getAllUsers() {
        return timeSlotService.getAllTimeSlots();
    }

    @GetMapping("/{id}")
    public TimeSlot getTimeSlotById(@PathVariable("id") Long id) {
        return timeSlotService.getTimeSlotById(id);
    }

    @PostMapping("/create-timeslot")
    public TimeSlot createTimeSlot(@RequestBody TimeSlot timeSlot) {
        return timeSlotService.saveOrUpdateTimeSlot(timeSlot);
    }

    @PutMapping("/{id}")
    public TimeSlot updateTimeSlot(@PathVariable("id") Long id, @RequestBody TimeSlot timeSlot) {
    	timeSlot.setId(id);
        return timeSlotService.saveOrUpdateTimeSlot(timeSlot);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeSlot(@PathVariable("id") Long id) {
        timeSlotService.deleteTimeSlot(id);
    }
}
