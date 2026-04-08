import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    private final Map<String, Student> students;
    private final Map<String, Instructor> instructors;
    private final Map<String, Admin> admins;
    private final List<IncidentReport> reports;
    private final List<Rule> rules;

    private String policies;
    private int nextReportId;

    public DataStore() {
        students = new LinkedHashMap<>();
        instructors = new LinkedHashMap<>();
        admins = new LinkedHashMap<>();
        reports = new ArrayList<>();
        rules = new ArrayList<>();
        nextReportId = 1;
        seedData();
    }

    private void seedData() {
        Course cp317 = new Course("CP317", "Software Engineering", "Winter 2026", 'A');
        Course cp220 = new Course("CP220", "Data Structures", "Winter 2026", 'B');
        Course uu150 = new Course("UU150", "Foundations", "Winter 2026", 'A');

        Student s1 = new Student("101202303", "pass123", "Scott Pilgrim", "pilg2303@mylaurier.ca", "Computer Science", 6);
        s1.addCourse(cp317.getCourseCode(), cp317.getSection());
        s1.addCourse(uu150.getCourseCode(), uu150.getSection());
        s1.addDocument("Essay 1 Submission");
        s1.addDocument("Lab Report 2");
        s1.addNotification(new Notification(1, "Possible plagiarism detected in Essay 1.", false, "Incident"));
        s1.addNotification(new Notification(2, "Meeting requested by instructor.", false, "Follow-up"));

        Student s2 = new Student("202101303", "pass123", "Matthew Patel", "pate2303@mylaurier.ca", "Computer Science", 2);
        s2.addCourse(cp317.getCourseCode(), cp317.getSection());
        s2.addDocument("Assignment 3 Submission");
        s2.addNotification(new Notification(3, "No new alerts.", true, "Info"));

        Student s3 = new Student("303202101", "pass123", "Ramona Flowers", "flow2101@mylaurier.ca", "Math", 8);
        s3.addCourse(cp220.getCourseCode(), cp220.getSection());
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

        rules.add(new Rule("Possible Plagiarism", "plagiarism", "Mark report for review"));
        rules.add(new Rule("Cheating Mention", "cheating", "Escalate severity"));
        rules.add(new Rule("Unauthorized AI Use", "ai-generated", "Flag for manual review"));

        createReport("101202303", "I2001", "CP317 A",
                "Essay Similarity Concern",
                "The student's essay contains several sections with wording that strongly resembles external sources without proper citation.");

        createReport("303202101", "I2002", "CP220 B",
                "Pattern Threshold Review",
                "Multiple irregularities were noticed across submissions. A broader review may be required because the student may have breached a reporting threshold.");
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

    public void addStudent(String stId, String pw, String stNm, String em, String enrPgrm) {
        Student s = new Student(stId, pw, stNm, em, enrPgrm, 1);
        students.put(s.getStudentId(), s);
    }

    public void addInstructor(String instId, String pw, String instNm, String em, String dpmt) {
        Instructor i = new Instructor(instId, pw, instNm, em, dpmt);
        instructors.put(i.getInstructorId(), i);
    }

    public void addRule(String ruleName, String keyword, String action) {
        rules.add(new Rule(ruleName, keyword, action));
    }

    public List<Rule> getAllRules() {
        return rules;
    }

    public IncidentReport createReport(String studentId, String instructorId, String course,
                                       String title, String description) {
        IncidentReport report = new IncidentReport(nextReportId, studentId, instructorId, course, title, description);
        nextReportId++;

        applyRules(report);

        Student student = students.get(studentId);
        if (student != null) {
            String message = "New incident report filed: " + title;
            if (!report.getMatchedRules().isEmpty()) {
                message += " | Matched rules: " + String.join(", ", report.getMatchedRules());
            }
            student.addNotification(new Notification(1000 + report.getReportId(), message, false, "Report"));
        }

        reports.add(report);
        return report;
    }

    private void applyRules(IncidentReport report) {
        String combined = (report.getTitle() + " " + report.getDescription()).toLowerCase();

        for (Rule rule : rules) {
            if (combined.contains(rule.getKeyword().toLowerCase())) {
                report.addMatchedRule(rule.getRuleName() + " (" + rule.getAction() + ")");
            }
        }

        if (!report.getMatchedRules().isEmpty()) {
            report.setStatus("Flagged by Rule");
        }
    }

    public List<IncidentReport> getReportsByStudent(String studentId) {
        List<IncidentReport> result = new ArrayList<>();
        for (IncidentReport report : reports) {
            if (report.getStudentId().equals(studentId)) {
                result.add(report);
            }
        }
        return result;
    }
}