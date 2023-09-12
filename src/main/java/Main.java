public class Main {
    public static void main(String[] args) {
        Login user = new Login();
        if (user.getUserIdx() >= 0) {
            Board b = new Board(user.getUserIdx());
        }
    }
}
