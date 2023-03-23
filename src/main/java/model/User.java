package model;

public class User {
    protected final String username, sare, passwordHash;

    public User(String username, String sare, String passwordHash) {
        this.username = username;
        this.sare = sare;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public String getSare() {
        return sare;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", sare='" + sare + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
