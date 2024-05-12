package tpo3.t3;
import java.util.List;
public class ElectronicJournal {
    private final List<Group> groups;
    private int weeksAmount;

    public ElectronicJournal(List<Group> groups, int weeksAmount) {
        this.groups = groups;
        this.weeksAmount = weeksAmount;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public int getWeeksAmount() {
        return weeksAmount;
    }

    public void printGrades() {
        for (int week = 1; week <= this.getWeeksAmount(); week++) {
            for (Group group : this.getGroups()) {
                for (Student student : group.getStudents()) {
                    System.out.println("Student " + group.getName() + " " + student.getName() + ", Grade on week " + week + ": " + student.getGradeByWeek(week));
                }
            }
        }
    }
}