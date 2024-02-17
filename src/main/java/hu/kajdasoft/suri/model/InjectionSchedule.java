package hu.kajdasoft.suri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class InjectionSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BodyPart bodyPart;
    private LocalDate injectionDate;
    private boolean injectionCompleted;

    public InjectionSchedule() {
    }

    public InjectionSchedule(BodyPart bodyPart, LocalDate injectionDate, boolean injectionCompleted) {
        this.bodyPart = bodyPart;
        this.injectionDate = injectionDate;
        this.injectionCompleted = injectionCompleted;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }

    public LocalDate getInjectionDate() {
        return injectionDate;
    }

    public void setInjectionDate(LocalDate injectionDate) {
        this.injectionDate = injectionDate;
    }

    public boolean isInjectionCompleted() {
        return injectionCompleted;
    }

    public void setInjectionCompleted(boolean injectionCompleted) {
        this.injectionCompleted = injectionCompleted;
    }
}
