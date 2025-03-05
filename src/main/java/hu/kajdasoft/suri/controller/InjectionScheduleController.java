package hu.kajdasoft.suri.controller;

import hu.kajdasoft.suri.model.InjectionSchedule;
import hu.kajdasoft.suri.service.InjectionScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedules")
public class InjectionScheduleController {
    @Autowired
    private InjectionScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<InjectionSchedule>> getCurrentInjection() {
        LocalDate today = LocalDate.now();
        List<InjectionSchedule> schedules = scheduleService.getAllSchedules();

        List<InjectionSchedule> relevantSchedules = schedules.stream()
                .filter(schedule -> !schedule.getInjectionDate().isBefore(today))
                .collect(Collectors.toList());

        System.out.println("Received schedules: " + relevantSchedules);
        return new ResponseEntity<>(relevantSchedules, HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}/complete")
    public ResponseEntity<Void> markScheduleAsCompleted(@PathVariable Long scheduleId) {
        System.out.println("Marking schedule as completed: " + scheduleId);
        scheduleService.markInjectionAsCompleted(scheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
