package hu.kajdasoft.suri.service;

import hu.kajdasoft.suri.model.InjectionSchedule;
import hu.kajdasoft.suri.repository.InjectionScheduleRepository;
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


    @Scheduled(cron = "0 */30 20-23 * * *")
    public void sendInjectionReminderEmails() {
        LocalDate today = LocalDate.now();
        InjectionSchedule schedule = scheduleRepository.findByInjectionDate(today);

        if (schedule != null && !schedule.isInjectionCompleted()) {
            System.out.println("Injection is not completed. Sending email...");
            emailSendingService.sendMail();
        } else {
            System.out.println("No need to send email. Injection is either completed or no schedule found.");
        }

    }
}
