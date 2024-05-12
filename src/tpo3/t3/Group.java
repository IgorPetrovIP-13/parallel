package tpo3.t3;
import java.util.List;

class Group {
    private String name;
    private List<Student> students;

    public Group(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public synchronized Student getNextStudentWithNoGradeForWeek(int week) {
        for (Student student : students) {
            if (!student.hasGrade(week)) {
                return student;
            }
        }
        return null;
    }
}