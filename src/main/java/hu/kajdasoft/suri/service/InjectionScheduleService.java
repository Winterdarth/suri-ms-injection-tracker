package hu.kajdasoft.suri.service;

import hu.kajdasoft.suri.model.BodyPart;
import hu.kajdasoft.suri.model.InjectionSchedule;
import hu.kajdasoft.suri.repository.InjectionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InjectionScheduleService {
    @Autowired
    private InjectionScheduleRepository scheduleRepository;

    // Method to retrieve all injection schedules
    public List<InjectionSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Method to implement rotation logic for body parts
    public BodyPart getNextBodyPart() {
        return BodyPart.getNextBodyPart();
    }

    // Method to mark an injection as completed
    public void markInjectionAsCompleted(Long scheduleId) {
        InjectionSchedule schedule = scheduleRepository.findById(scheduleId).orElse(null);

        if (schedule != null) {
            schedule.setInjectionCompleted(true);
            scheduleRepository.save(schedule);
        }
    }

    // Method to generate injection schedules for the next year
    public void generateInjectionSchedulesForNextYear() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            // Generate injection schedule for currentDate
            BodyPart bodyPart = getNextBodyPart();
            InjectionSchedule schedule = new InjectionSchedule(bodyPart, currentDate, false);
            scheduleRepository.save(schedule);

            // Move to the next injection date (every second day)
            currentDate = currentDate.plusDays(2);
        }
    }
}
