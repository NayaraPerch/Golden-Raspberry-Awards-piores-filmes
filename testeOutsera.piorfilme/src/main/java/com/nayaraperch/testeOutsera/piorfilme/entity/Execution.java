package com.nayaraperch.testeOutsera.piorfilme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Execution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long execution_id;

    private Date executionStart;

    private Date executionEnd;

    private Status status;

    private String message;

    public Execution() {
    }

    public Execution(Date executionStart, Date executionEnd, Status status, String message) {
        this.executionStart = executionStart;
        this.executionEnd = executionEnd;
        this.status = status;
        this.message = message;
    }

    public Long getExecution_id() {
        return execution_id;
    }

    public void setExecution_id(Long execution_id) {
        this.execution_id = execution_id;
    }

    public Date getExecutionStart() {
        return executionStart;
    }

    public void setExecutionStart(Date executionStart) {
        this.executionStart = executionStart;
    }

    public Date getExecutionEnd() {
        return executionEnd;
    }

    public void setExecutionEnd(Date executionEnd) {
        this.executionEnd = executionEnd;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
