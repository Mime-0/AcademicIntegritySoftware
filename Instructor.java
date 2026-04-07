import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private final String instructorId;
    private final String password;
    private final String instructorName;
    private final String email;
    private final String department;
    private final List<String> courses;

    public Instructor(String instructorId, String password, String instructorName,
                      String email, String department) {
        this.instructorId = instructorId;
        this.password = password;
        this.instructorName = instructorName;
        this.email = email;
        this.department = department;
        this.courses = new ArrayList<>();
    }

    public String getInstructorId() {
        return instructorId;
    }

    public String getPassword() {
        return password;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void addCourse(String courseCode) {
        courses.add(courseCode);
    }
}