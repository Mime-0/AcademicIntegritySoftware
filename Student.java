import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String studentId;
    private final String password;
    private final String studentName;
    private final String email;
    private final String enrolledProgram;
    private int integrityStatus;
    private final List<String> documents;
    private final List<Notification> notifications;
    private final List<String> courses;

    public Student(String studentId, String password, String studentName,
                   String email, String enrolledProgram, int integrityStatus) {
        this.studentId = studentId;
        this.password = password;
        this.studentName = studentName;
        this.email = email;
        this.enrolledProgram = enrolledProgram;
        this.integrityStatus = integrityStatus;
        this.documents = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPassword() {
        return password;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getEnrolledProgram() {
        return enrolledProgram;
    }

    public int getIntegrityStatus() {
        return integrityStatus;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void addDocument(String document) {
        documents.add(document);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void addCourse(String courseCode) {
        courses.add(courseCode);
    }
}