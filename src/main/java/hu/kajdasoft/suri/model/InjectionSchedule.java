package hu.kajdasoft.suri.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class InjectionSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private BodyPart bodyPart;
    private LocalDate injectionDate;
    private boolean injectionCompleted;

    public InjectionSchedule(BodyPart bodyPart, LocalDate injectionDate, boolean injectionCompleted) {
        this.bodyPart = bodyPart;
        this.injectionDate = injectionDate;
        this.injectionCompleted = injectionCompleted;
    }

    public InjectionSchedule() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "InjectionSchedule{" +
                "id=" + id +
                ", injectionDate='" + injectionDate + '\'' +
                ", bodyPart='" + bodyPart + '\'' +
                '}';
    }
}
