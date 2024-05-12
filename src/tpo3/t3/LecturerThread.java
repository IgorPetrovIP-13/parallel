package tpo3.t3;

public class LecturerThread extends Thread {

    private ElectronicJournal journal;

    public LecturerThread(ElectronicJournal journal) {
        this.journal = journal;
    }

    @Override
    public void run() {
        for (int week = 1; week <= journal.getWeeksAmount(); week++) {
                for (Group group : journal.getGroups()) {
                    Student student;
                    while ((student = group.getNextStudentWithNoGradeForWeek(week)) != null) {
                        synchronized (student) {
                            if (!student.hasGrade(week)) {
                                int grade = generateGrade();
                                student.setGrade(week, grade);
                                System.out.println(currentThread().getName() + " set grade " + grade + " " + student.getName() + " / Week " + week);
                            }
                        }
                    }
                }try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int generateGrade() {
        return (int) (Math.random() * 100) + 1;
    }
}
