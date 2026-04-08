public class Course {
    private final String courseCode;
    private final String courseName;
    private final String term;
    private final char section;

    public Course(String courseCode, String courseName, String term, char section) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.term = term;
        this.section = section;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTerm() {
        return term;
    }

    public char getSection() {
        return section;
    }
}
