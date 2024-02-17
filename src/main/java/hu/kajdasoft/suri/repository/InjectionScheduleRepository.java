package hu.kajdasoft.suri.repository;

import hu.kajdasoft.suri.model.InjectionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InjectionScheduleRepository extends JpaRepository<InjectionSchedule, Long> {
    // Custom query method to retrieve injection schedules by injection date
    InjectionSchedule findByInjectionDate(LocalDate injectionDate);
}
