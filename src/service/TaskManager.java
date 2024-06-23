package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {

    private int nextId = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public int generateId() {
        return ++nextId;
    }


    // Получение списка всех задач
    public void getAllTask() {
        System.out.println(tasks);
    }

    public void getAllEpic() {
        System.out.println(epics);
    }

    public void getAllSubTask() {
        System.out.println(subTasks);
    }


    // Удаление всех задач
    public void clearTask() {
        tasks.clear();
    }

    public void clearEpic() {
        epics.clear();
        subTasks.clear();
    }

    public void clearSubTask() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.clearSubTasks();
            epic.setStatus(Status.NEW);
        }
    }


    // Получение по идентификатору
    public Task getIdTask(int id) {
        return tasks.get(id);
    }

    public Epic getIdEpic(int id) {
        return epics.get(id);
    }

    public SubTask getIdSubTask(int id) {
        return subTasks.get(id);
    }


    //Создание объекта
    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateId());
        Epic epic = epics.get(subTask.getEpicId());
        epic.addSubTasks(subTask);
        subTasks.put(subTask.getId(), subTask);
        updateStatusEpic(epic);
        return subTask;
    }


    // Обновление объекта
    public Task updateTask(Task task) {
        Integer taskId = task.getId();

        if (!tasks.containsKey(taskId)) {
            return null;
        }

        tasks.replace(taskId, task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        Integer epicId = epic.getId();

        if (!epics.containsKey(epicId)) {
            return null;
        }

        Epic oldEpic = epics.get(epicId);
        ArrayList<SubTask> oldEpicSubTask = oldEpic.getSubTasksId();
        if (!oldEpicSubTask.isEmpty()) {
            for (SubTask subTask : oldEpicSubTask) {
                subTasks.remove(subTask.getId());
            }
        }

        epics.replace(epicId, epic);

        ArrayList<SubTask> newEpicSubTask = epic.getSubTasksId();
        if (!newEpicSubTask.isEmpty()) {
            for (SubTask subTask : newEpicSubTask) {
                subTasks.put(subTask.getId(), subTask);
            }
        }

        updateStatusEpic(epic);
        return epic;
    }

    public SubTask updateSubTask(SubTask subTask) {
        Integer subTaskId = subTask.getId();

        if (!subTasks.containsKey(subTaskId)) {
            return null;
        }

        int epicId = subTask.getEpicId();
        SubTask oldSubTask = subTasks.get(subTaskId);
        subTasks.replace(subTaskId, subTask);

        Epic epic = epics.get(epicId);
        ArrayList<SubTask> subTasksId = epic.getSubTasksId();
        subTasksId.remove(oldSubTask);
        subTasksId.add(subTask);
        epic.setSubTasksId(subTasksId);
        updateStatusEpic(epic);
        return subTask;
    }


    // Удаление по идентификатору
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeEpicById(int id) {
        ArrayList<SubTask> epicSubtasks = epics.get(id).getSubTasksId();
        epics.remove(id);
        for (SubTask subtask : epicSubtasks) {
            subTasks.remove(subtask.getId());
        }
    }

    public void removeSubTaskById(int id) {
        SubTask subTaskId = subTasks.get(id);
        int epicID = subTaskId.getEpicId();
        subTasks.remove(id);

        Epic epic = epics.get(epicID);
        ArrayList<SubTask> subtaskList = epic.getSubTasksId();
        subtaskList.remove(subTaskId);
        epic.setSubTasksId(subtaskList);
        updateStatusEpic(epic);
    }


    // Управление статусами
    public void updateStatusEpic(Epic epic) {
        int countDone = 0;
        int countNew = 0;

        ArrayList<SubTask> list = epic.getSubTasksId();

        for (SubTask subTask : list) {
            if (subTask.getStatus() == Status.DONE) {
                countDone++;
            }
            if (subTask.getStatus() == Status.NEW) {
                countNew++;
            }
        }

        if (countDone == list.size()) {
            epic.setStatus(Status.DONE);
        } else if (countNew == list.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
