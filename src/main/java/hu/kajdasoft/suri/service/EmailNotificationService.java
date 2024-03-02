package hu.kajdasoft.suri.service;

import hu.kajdasoft.suri.model.InjectionSchedule;
import hu.kajdasoft.suri.repository.InjectionScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailNotificationService {
    @Autowired
    private InjectionScheduleRepository scheduleRepository;

    @Autowired
    private EmailSendingService emailSendingService;
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    @Scheduled(cron = "0 0 17-23 * * *")
    //@Scheduled(initialDelay = 0, fixedDelay = 120000) // Run immediately, then every 2 minutes
    public void sendInjectionReminderEmails() {
        LocalDate today = LocalDate.now();
        InjectionSchedule schedule = scheduleRepository.findByInjectionDate(today);

        if (schedule != null && !schedule.isInjectionCompleted()) {
            emailSendingService.sendMail();
        }
    }
}
