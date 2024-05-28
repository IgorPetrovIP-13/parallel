package tpo3.t3;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Group group1 = new Group("IP-01", generateStudents(1, 6));
        Group group2 = new Group("IP-02", generateStudents(2, 6));
        Group group3 = new Group("IP-03", generateStudents(3, 6));

        List<Group> groups = new ArrayList<>(List.of(group1, group2, group3));
        ElectronicJournal journal = new ElectronicJournal(groups, 3);

        LecturerThread lecturer1 = new LecturerThread(journal);
        LecturerThread lecturer2 = new LecturerThread(journal);
        LecturerThread lecturer3 = new LecturerThread(journal);
        LecturerThread lecturer4 = new LecturerThread(journal);

        lecturer1.start();
        lecturer2.start();
        lecturer3.start();
        lecturer4.start();

        try {
            lecturer1.join();
            lecturer2.join();
            lecturer3.join();
            lecturer4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        journal.printGrades();
    }

    private static List<Student> generateStudents(int groupNum, int amount) {
        List<Student> students = new ArrayList<>();

        for (int i=0; i<amount; i++){
            students.add(new Student(String.format("Student %d.%d", groupNum, i+1)));
        }
        return students;
    }
}