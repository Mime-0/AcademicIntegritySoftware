public class Rule {
    private final String ruleName;
    private final String keyword;
    private final String action;

    public Rule(String ruleName, String keyword, String action) {
        this.ruleName = ruleName;
        this.keyword = keyword;
        this.action = action;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return ruleName + " | keyword: " + keyword + " | action: " + action;
    }
}