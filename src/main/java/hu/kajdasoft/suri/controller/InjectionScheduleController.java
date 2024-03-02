package hu.kajdasoft.suri.controller;

import hu.kajdasoft.suri.model.InjectionSchedule;
import hu.kajdasoft.suri.service.InjectionScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/schedules")
public class InjectionScheduleController {
    @Autowired
    private InjectionScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<InjectionSchedule>> getCurrentInjection() {
        List<InjectionSchedule> schedules = scheduleService.getAllSchedules();
        System.out.println("Recevied schedules: " + schedules);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}/complete")
    public ResponseEntity<Void> markScheduleAsCompleted(@PathVariable Long scheduleId) {
        scheduleService.markInjectionAsCompleted(scheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
