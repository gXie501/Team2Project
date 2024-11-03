public interface UserInterface {
    public void createUser(String username, String password, String pfp, boolean restrictMessage);
    public boolean login(String username, String password);
    public boolean blockUser(User user, User blockUser);
    public boolean friendUser(User user, User friendUser);
}
