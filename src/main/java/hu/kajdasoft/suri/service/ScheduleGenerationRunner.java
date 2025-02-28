package hu.kajdasoft.suri.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ScheduleGenerationRunner implements ApplicationRunner {

    private final InjectionScheduleService scheduleService;

    public ScheduleGenerationRunner(InjectionScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       // scheduleService.generateInjectionSchedulesForNextYear();
        // scheduleService.resetInjectionCompletedFlags();
    }
}
