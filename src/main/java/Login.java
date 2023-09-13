import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Login {
    private Scanner sc;
    private int func;
    private User user;
    private ArrayList<User> userlist;
    private int userIdx;
    private String userName;

    Login() {
        this.sc = new Scanner(System.in);
        this.func = 0;
        this.userlist = new ArrayList<>();
        this.userIdx = -1;

        loadData();
        showMain();
        save();
    }

    public void loadData() {
        File f = new File("userdata.dat");
        if (f.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader("userdata.dat"));
                while (true) {
                    String str = br.readLine();
                    if (str == null) break;
                    user = new User();
                    user.setId(str);
                    user.setPassword(br.readLine());
                    user.setName(br.readLine());
                    userlist.add(user);
                }
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showMain() {
        System.out.println("===  Login  ===");
        System.out.println("1. 로그인, 2. 회원가입, 3. 회원탈퇴, 4. 게스트모드");
        while (true) {
            System.out.print("입력 : ");
            func = inputNum(sc);
            if (func == 4) {
                this.userIdx = -1;
                return;
            } else if (func == 2) register();
            else if (func == 1) {
                if (login()) {
                    return;
                }
            } else if (func == 3) withdraw();
        }
    }

    public void withdraw() {
        System.out.println("회원탈퇴");
        System.out.print("탈퇴할 아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력해주세요 : ");
        String pw = sc.nextLine();
        userIdx = findUser(id, pw);
        if (userIdx < 0) System.out.println("회원정보가 맞지 않습니다");
        else {
            System.out.println(userlist.get(userIdx).getName() + "님 계정이 삭제되었습니다.");
            userlist.remove(userIdx);
        }
    }

    public boolean login() {
        System.out.print("아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력해주세요 : ");
        String pw = sc.nextLine();
        userIdx = findUser(id, pw);
        if (userIdx < 0) {
            System.out.println("회원정보가 맞지 않습니다.");
            return false;
        } else {
            userName = userlist.get(userIdx).getName();
            System.out.println(userName + "님 환영합니다!");
            return true;
        }
    }

    public void register() {
        System.out.println("회원가입");
        System.out.print("사용하실 아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        if (findUser(id, "") != -2) {
            System.out.println("이미 존재하는 아이디입니다.");
        } else {
            user = new User();
            user.setId(id);
            System.out.print("사용하실 비밀번호를 입력해주세요 : ");
            user.setPassword(sc.nextLine());
            System.out.print("성함을 입력해주세요 : ");
            user.setName(sc.nextLine());
            userlist.add(user);
            System.out.println("회원가입이 완료되었습니다.");
        }
    }

    public int findUser(String id, String pw) {
        for (int i = 0; i < userlist.size(); i++) {
            if (userlist.get(i).getId().equals(id)) {
                if (userlist.get(i).getPassword().equals(pw)) return i;
                else return -1;
            }
        }
        return -2;
    }


    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public void save() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("userdata.dat"));
            for (User arr : userlist) {
                pw.println(arr.getId());
                pw.println(arr.getPassword());
                pw.println(arr.getName());
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int inputNum(Scanner sc) {
        int input;
        while (true) {
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
                return -1;
            }
            sc.nextLine();
            return input;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
