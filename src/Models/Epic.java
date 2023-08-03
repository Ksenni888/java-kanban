package models;

import repositories.SubtaskRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Epic extends Task {

    private int durationEpic;

    private LocalDateTime startTimeEpic;

    private LocalDateTime endTimeEpic;
    private SubtaskRepository subtaskRepository = new SubtaskRepository();
    private ArrayList<Integer> idSubtask = new ArrayList<>();

    public int getDurationEpic(int id) {
        durationEpic = 0;// продолжительность эпика
        for (Subtask s : subtaskRepository.getListSubtask(id)) {
            durationEpic = durationEpic + s.getDuration();
        }

        return durationEpic;
    }

    public LocalDateTime getStartTimeEpic(int id) {
        LocalDateTime time = LocalDateTime.of(2028, 8, 2, 0, 0, 0);
        for (Subtask s : subtaskRepository.getListSubtask(id)) {
            if (s.getStartTime() != null) {
                if (s.getStartTime().isBefore(time)) {
                    time = s.getStartTime();
                }
            } else time = LocalDateTime.of(2028, 8, 2, 0, 0, 0);
        }

        return time;
    }

    public LocalDateTime getEndTimeEpic(int id) {
        LocalDateTime time = LocalDateTime.of(2000, 8, 2, 0, 0, 0);
        for (Subtask s : subtaskRepository.getListSubtask(id)) {
            if (s.getEndTime().isAfter(time)) {
                time = s.getEndTime();
            }
        }

        return time;
    }

    public ArrayList<Integer> getIdSubtask() {
        return idSubtask;
    }

    public void setStartTimeEpic(LocalDateTime startTimeEpic) {
        this.startTimeEpic = startTimeEpic;
    }

    public void setEndTimeEpic(LocalDateTime endTimeEpic) {
        this.endTimeEpic = endTimeEpic;
    }

    public void setDurationEpic(int durationEpic) {
        this.durationEpic = durationEpic;
    }

    public void setIdSubtask(int id) {
        idSubtask.add(id);
    }

    @Override
    public String toString() {
        return "id=[" + getId() + "]" + " name=[" + getName() + "] статус задачи=[" + getStatus() + "] StartTime=[" + getStartTimeEpic(getId()) + "] продолжительность (мин.)=[" + getDurationEpic(getId()) + "] EndTime=[" + getEndTimeEpic(getId()) + "]";
    }

}
