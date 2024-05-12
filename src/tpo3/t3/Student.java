package tpo3.t3;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private Map<Integer, Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new HashMap<>();
    }

    public void setGrade(int week, int grade) {
        grades.put(week, grade);
    }

    public int getGradeByWeek(int week) {
        return grades.get(week);
    }

    public boolean hasGrade(int week) {
        return grades.containsKey(week);
    }

    public String getName() {
        return name;
    }

}