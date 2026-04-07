public class Admin {
    private final String adminId;
    private final String password;
    private final String otp;
    private final String adminName;

    public Admin(String adminId, String password, String otp, String adminName) {
        this.adminId = adminId;
        this.password = password;
        this.otp = otp;
        this.adminName = adminName;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getPassword() {
        return password;
    }

    public String getOtp() {
        return otp;
    }

    public String getAdminName() {
        return adminName;
    }
}
