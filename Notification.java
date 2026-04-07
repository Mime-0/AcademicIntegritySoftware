public class Notification {
    private final int alertId;
    private final String message;
    private boolean acknowledged;
    private final String alertType;

    public Notification(int alertId, String message, boolean acknowledged, String alertType) {
        this.alertId = alertId;
        this.message = message;
        this.acknowledged = acknowledged;
        this.alertType = alertType;
    }

    public int getAlertId() {
        return alertId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public String getAlertType() {
        return alertType;
    }

    public void acknowledge() {
        acknowledged = true;
    }
}