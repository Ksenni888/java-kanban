package models;

import org.junit.platform.engine.support.hierarchical.EngineExecutionContext;
import java.time.LocalDateTime;

public class Task implements EngineExecutionContext {
    private int id;
    private String name;
    private String description;

    private int duration;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public Status status;

  /*  public Task(int id, String name, String description, int duration, LocalDateTime startTime, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
        this.status = status;
    }*/


    public int getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        if (startTime != null) {
            return startTime;
        } else {
            startTime = LocalDateTime.of(5001,1, 1, 0, 0, 0);
            return startTime;
        }
    }


    public LocalDateTime getEndTime() {
        endTime = getStartTime().plusMinutes(getDuration());
        return endTime;
    }

    public int getId() {
        return this.id;
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime != null) {
            this.startTime = startTime;
        } else {
            this.startTime = LocalDateTime.of(5001, 1, 1, 0, 0, 0);
        }

    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Task setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "id=[" + id + "]" + " name=[" + name + "] статус задачи=[" + status + "] StartTime=[" + startTime + "] продолжительность (мин.)=[" + duration + "] EndTime=[" + getEndTime() + "]";
    }

}




