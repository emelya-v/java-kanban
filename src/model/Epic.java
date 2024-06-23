package model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<SubTask> subTasksId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<SubTask> getSubTasksId() {
        return subTasksId;
    }

    public void setSubTasksId(ArrayList<SubTask> subTasksId) {
        this.subTasksId = subTasksId;
    }

    public void addSubTasks(SubTask subTask) {
        subTasksId.add(subTask);
    }

    public void clearSubTasks() {
        subTasksId.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name= " + getName() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", subTaskId.size = " + subTasksId.size() +
                ", status = " + getStatus() +
                '}';
    }
}
