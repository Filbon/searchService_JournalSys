package com.example.DTO;

import java.time.LocalDateTime;

public class EncounterDTO {
    private Long id;
    private LocalDateTime date;
    private String reason;
    private String outcome;
    private Long patientId;
    private Long practitionerId;

    private String patientName;

    public EncounterDTO() {}

    public EncounterDTO(Long id, LocalDateTime date, String reason, String outcome, Long patientId, Long practitionerId) {
        this.id = id;
        this.date = date;
        this.reason = reason;
        this.outcome = outcome;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Long practitionerId) {
        this.practitionerId = practitionerId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
