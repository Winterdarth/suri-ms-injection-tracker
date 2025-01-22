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
    private static int currentIndex = 0;

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

    public void generateInjectionSchedulesForNextYear() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            BodyPart bodyPart = getNextBodyPart(currentIndex);
            InjectionSchedule schedule = new InjectionSchedule(bodyPart, currentDate, false);
            scheduleRepository.save(schedule);

            currentDate = currentDate.plusDays(2);

            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                currentDate = currentDate.plusDays(2);
            } else if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                currentDate = currentDate.plusDays(1);
            }

            currentIndex = (currentIndex + 1) % BodyPart.values().length;
        }
    }

}
