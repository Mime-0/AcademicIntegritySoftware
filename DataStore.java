import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    private final Map<String, Student> students;
    private final Map<String, Instructor> instructors;
    private final Map<String, Admin> admins;
    private String policies;

    public DataStore() {
        students = new LinkedHashMap<>();
        instructors = new LinkedHashMap<>();
        admins = new LinkedHashMap<>();
        seedData();
    }

    private void seedData() {
        Course cp317 = new Course("CP317", "Software Engineering", "Winter 2026", 'A');
        Course cp220 = new Course("CP220", "Data Structures", "Winter 2026", 'B');
        Course uu150 = new Course("UU150", "Foundations", "Winter 2026", 'A');

        Student s1 = new Student("101202303", "pass123", "Scott Pilgrim", "pilg2303@mylaurier.ca", "Computer Science", 6);
        s1.addCourse(cp317.getCourseCode());
        s1.addCourse(uu150.getCourseCode());
        s1.addDocument("Essay 1 Submission");
        s1.addDocument("Lab Report 2");
        s1.addNotification(new Notification(1, "Possible plagiarism detected in Essay 1.", false, "Incident"));
        s1.addNotification(new Notification(2, "Meeting requested by instructor.", false, "Follow-up"));

        Student s2 = new Student("202101303", "pass123", "Matthew Patel", "pate2303@mylaurier.ca", "Computer Science", 2);
        s2.addCourse(cp317.getCourseCode());
        s2.addDocument("Assignment 3 Submission");
        s2.addNotification(new Notification(3, "No new alerts.", true, "Info"));

        Student s3 = new Student("303202101", "pass123", "Ramona Flowers", "flow2101@mylaurier.ca", "Math", 8);
        s3.addCourse(cp220.getCourseCode());
        s3.addDocument("Final Project Draft");
        s3.addNotification(new Notification(4, "Repeat integrity pattern threshold reached.", false, "Threshold"));

        students.put(s1.getStudentId(), s1);
        students.put(s2.getStudentId(), s2);
        students.put(s3.getStudentId(), s3);

        Instructor i1 = new Instructor("I2001", "teach123", "Dr. Schlatt", "schlatt@mylaurier.ca", "Computer Science");
        i1.addCourse(cp317.getCourseCode(), cp317.getSection());
        i1.addCourse(uu150.getCourseCode(), uu150.getSection());

        Instructor i2 = new Instructor("I2002", "teach123", "Prof. Chiu", "chiu@mylaurier.ca", "Computer Science");
        i2.addCourse(cp220.getCourseCode(), cp220.getSection());

        instructors.put(i1.getInstructorId(), i1);
        instructors.put(i2.getInstructorId(), i2);

        Admin a1 = new Admin("A3001", "admin123", "9999", "System Admin");
        admins.put(a1.getAdminId(), a1);

        policies =
            "Policy 1: Students must submit original work.\n" +
            "Policy 2: Instructors may flag integrity-related incidents.\n" +
            "Policy 3: Repeated incidents may trigger disciplinary review.\n" +
            "Policy 4: Administrators may update policy references and access rules.";
    }

    public Student authenticateStudent(String id, String password) {
        Student student = students.get(id);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        return null;
    }

    public Instructor authenticateInstructor(String id, String password) {
        Instructor instructor = instructors.get(id);
        if (instructor != null && instructor.getPassword().equals(password)) {
            return instructor;
        }
        return null;
    }

    public Admin authenticateAdmin(String id, String password, String otp) {
        Admin admin = admins.get(id);
        if (admin != null &&
            admin.getPassword().equals(password) &&
            admin.getOtp().equals(otp)) {
            return admin;
        }
        return null;
    }

    public Student getStudentById(String id) {
        return students.get(id);
    }

    public Instructor getInstructorById(String id) {
        return instructors.get(id);
    }

    public List<Student> getStudentsByCourse(String courseCode) {
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getCourses().contains(courseCode)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors.values());
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }
}
