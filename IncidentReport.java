import java.util.ArrayList;
import java.util.List;

public class IncidentReport {
    private final int reportId;
    private final String studentId;
    private final String instructorId;
    private final String course;
    private final String title;
    private final String description;
    private String status;
    private final List<String> matchedRules;

    public IncidentReport(int reportId, String studentId, String instructorId,
                          String course, String title, String description) {
        this.reportId = reportId;
        this.studentId = studentId;
        this.instructorId = instructorId;
        this.course = course;
        this.title = title;
        this.description = description;
        this.status = "Submitted";
        this.matchedRules = new ArrayList<>();
    }

    public int getReportId() {
        return reportId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public String getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getMatchedRules() {
        return matchedRules;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addMatchedRule(String rule) {
        matchedRules.add(rule);
    }
}