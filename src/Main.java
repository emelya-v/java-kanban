import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

       TaskManager taskManager = new TaskManager();

       Task task1 = new Task("Задача 1", "Описание задачи 1");
       taskManager.createTask(task1);
       Task task2 = new Task("Задача 2", "Описание задачи 2", Status.IN_PROGRESS);
       taskManager.createTask(task2);


       Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
       taskManager.createEpic(epic1);
       SubTask subTask1 = new SubTask("Подзадача 1", "Описание подазадачи 1", epic1.getId());
       taskManager.createSubTask(subTask1);
       SubTask subTask2 = new SubTask("Подзадача 2", "Описание подазадачи 2", Status.IN_PROGRESS, epic1.getId());
       taskManager.createSubTask(subTask2);

       Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
       taskManager.createEpic(epic2);
       SubTask subTask3 = new SubTask("Подзадача 3", "Описание подазадачи 3", epic2.getId());
       taskManager.createSubTask(subTask3);

       taskManager.getAllTask();
       taskManager.getAllEpic();
       taskManager.getAllSubTask();

       task1.setStatus(Status.IN_PROGRESS);
       subTask3.setStatus(Status.DONE);
       taskManager.updateTask(task1);
       taskManager.updateSubTask(subTask3);

       taskManager.getAllTask();
       taskManager.getAllEpic();
       taskManager.getAllSubTask();

       taskManager.removeTaskById(2);
       taskManager.removeEpicById(3);
       taskManager.getAllTask();
       taskManager.getAllEpic();

        /*
        1) Создайте две задачи
        2) Эпик с двумя подзадачами
        3) Эпик с одной подзадачей.
        4) Распечатайте списки эпиков, задач и подзадач через System.out.println(..).
        5) Измените статусы созданных объектов, распечатайте их.
        6) Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
        7) наконец, попробуйте удалить одну из задач и один из эпиков.
        */
    }
}
