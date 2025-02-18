package application;

public class User {
    private boolean isAdmin;

    public User(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
