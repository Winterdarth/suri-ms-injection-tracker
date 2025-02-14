package hu.kajdasoft.suri.service;

import hu.kajdasoft.suri.model.BodyPart;
import hu.kajdasoft.suri.model.InjectionSchedule;
import hu.kajdasoft.suri.repository.InjectionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class InjectionScheduleService {

    @Autowired
    private InjectionScheduleRepository scheduleRepository;

    public List<InjectionSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public BodyPart getNextBodyPart(int index) {
        return BodyPart.getNextBodyPart(index);
    }

    public void markInjectionAsCompleted(Long scheduleId) {
        InjectionSchedule schedule = scheduleRepository.findById(scheduleId).orElse(null);

        if (schedule != null) {
            schedule.setInjectionCompleted(true);
            scheduleRepository.save(schedule);
        }
    }

    private int getLastUsedBodyPartIndex() {
        InjectionSchedule lastSchedule = scheduleRepository.findTopByOrderByInjectionDateDesc();
        if (lastSchedule != null) {
            return lastSchedule.getBodyPart().getIndex();
        }
        return 0;
    }
    public void resetInjectionCompletedFlags() {
        LocalDate today = LocalDate.now();
        List<InjectionSchedule> schedules = scheduleRepository.findAll();

        for (InjectionSchedule schedule : schedules) {
            if (schedule.getInjectionDate().isAfter(today)) {
                schedule.setInjectionCompleted(false); // Reset flag for future schedules
                scheduleRepository.save(schedule);
            }
        }
    }

    public void generateInjectionSchedulesForNextYear() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        LocalDate currentDate = startDate;
        int lastUsedIndex = getLastUsedBodyPartIndex();
        while (currentDate.isBefore(endDate)) {
            BodyPart bodyPart = getNextBodyPart(lastUsedIndex);
            InjectionSchedule schedule = new InjectionSchedule(bodyPart, currentDate, false); // Ensure injectionCompleted is false
            scheduleRepository.save(schedule);

            currentDate = currentDate.plusDays(2);

            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                currentDate = currentDate.plusDays(2);
            } else if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                currentDate = currentDate.plusDays(1);
            }

            lastUsedIndex = (lastUsedIndex + 1) % BodyPart.values().length;
        }
    }

    /*
    public void generateInjectionSchedulesForNextYear() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        LocalDate currentDate = startDate;
        int lastUsedIndex = getLastUsedBodyPartIndex();
        while (currentDate.isBefore(endDate)) {
            BodyPart bodyPart = getNextBodyPart(lastUsedIndex);
            InjectionSchedule schedule = new InjectionSchedule(bodyPart, currentDate, false);
            scheduleRepository.save(schedule);

            currentDate = currentDate.plusDays(2);

            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                currentDate = currentDate.plusDays(2);
            } else if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                currentDate = currentDate.plusDays(1);
            }

            lastUsedIndex = (lastUsedIndex + 1) % BodyPart.values().length;
        }
    }
*/



}
