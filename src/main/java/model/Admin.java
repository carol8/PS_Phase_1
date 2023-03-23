package model;

public class Admin extends User{
    public Admin(String username, String sare, String passwordHash) {
        super(username, sare, passwordHash);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", sare='" + sare + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
