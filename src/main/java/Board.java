import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Board {
    private Scanner sc;
    private ArrayList<Post> postlist;
    private int userIdx;

    Board(int userIdx) {
        this.userIdx = userIdx;
        sc = new Scanner(System.in);
        postlist = new ArrayList<>();
        File f = new File(userIdx + ".dat");
        if (f.exists()) load();
        showMain();
        save();
    }

    public void showMain() {
        System.out.println("===  TextBoard  ===");
        while (true) {
            System.out.print("메뉴 입력 : ");
            String func = sc.nextLine();

            if (func.equals("exit")) {
                save();
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (func.equals("add")) addPost();

            else if (func.equals("list")) {
                if (postlist.isEmpty()) {
                    System.out.println("게시물이 없습니다.");
                } else {
                    printPosts();
                }
            } else if (func.equals("update")) {
                System.out.print("수정할 게시물 번호 : ");
                int postNum = inputNum(sc);
                if (postNum == -1) continue;
                int idx = findPost(postNum);
                if (idx == -1) System.out.println("없는 게시물 번호입니다.");
                else {
                    updatePost(idx);
                    System.out.println(postNum + "번 게시물이 수정되었습니다.");
                }
            } else if (func.equals("delete")) {
                System.out.print("삭제할 게시물 번호 : ");
                int postNum = inputNum(sc);
                if (postNum == -1) continue;
                int idx = findPost(postNum);
                if (idx == -1) System.out.println("없는 게시물 번호입니다.");
                else {
                    postlist.remove(idx);
                    printPosts();
                    System.out.println(postNum + "번 게시물이 삭제되었습니다.");
                }
            } else if (func.equals("detail")) {
                System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
                int postNum = inputNum(sc);
                if (postNum == -1) continue;
                int idx = findPost(postNum);
                if (idx == -1) System.out.println("없는 게시물 번호입니다.");
                else {
                    postlist.get(idx).viewUp();
                    printDetail(idx);
                    while (true) {
                        System.out.println("상세보기 기능을 선택해주세요(1. 댓글 등록, 2. 추천, 3. 수정, 4. 삭제, 5. 목록으로)");
                        System.out.print("입력 : ");
                        int num = inputNum(sc);
                        if (num == -1) continue;
                        else if (num == 1) {
                            postlist.get(idx).addComment();
                            System.out.println("댓글이 성공적으로 등록되었습니다.");
                            break;
                        } else if (num == 2) {
                            postlist.get(idx).greatUp(userIdx);
                            break;
                        } else if (num == 3) {
                            updatePost(idx);
                            System.out.println("해당 게시물이 수정되었습니다.");
                            break;
                        } else if (num == 4) {
                            postlist.remove(idx);
                            printPosts();
                            System.out.println("해당 게시물이 삭제되었습니다.");
                            break;
                        } else if (num == 5) break;
                    }
                }
            } else if (func.equals("search")) {
                System.out.print("검색 키워드를 입력해주세요 : ");
                searchPost(sc.nextLine());
            }
        }
    }

    public void save() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(userIdx + ".dat"));
            pw.println(postlist.size());
            for (Post post : postlist) {
                pw.println(post.getTitle());
                pw.println(post.getDetail());
                pw.println(post.getCount());
                pw.println(post.getViews());
                pw.println(post.getGreat());
                pw.println(post.getTime());
                post.save(pw);
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(userIdx + ".dat"));
            int postlistSize = Integer.parseInt(br.readLine());
            postlist = new ArrayList<>(postlistSize);
            for (int i = 0; i < postlistSize; i++) {
                Post post = new Post();
                post.setTitle(br.readLine());
                post.setDetail(br.readLine());
                post.setCount(Integer.parseInt(br.readLine()));
                post.setViews(Integer.parseInt(br.readLine()));
                post.setGreat(Integer.parseInt(br.readLine()));
                post.setTime(br.readLine());
                post.load(br);
                postlist.add(post);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPost() {
        Scanner sc = new Scanner(System.in);

        System.out.print("게시물 제목을 입력해주세요 : ");
        String title = sc.nextLine();
        System.out.print("게시물 내용을 입력해주세요 : ");
        String detail = sc.nextLine();
        setPost(title, detail);
        System.out.println("게시물이 등록되었습니다.");
    }

    public void updatePost(int idx) {
        Scanner sc = new Scanner(System.in);

        System.out.print("수정후 제목 : ");
        String title = sc.nextLine();
        System.out.print("수정후 내용 : ");
        String detail = sc.nextLine();

        postlist.get(idx).setTitle(title);
        postlist.get(idx).setDetail(detail);
        LocalDateTime now = LocalDateTime.now();
        postlist.get(idx).setTime(now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")));
    }

    public int findPost(int postNum) {
        for (int idx = 0; idx < postlist.size(); idx++) {
            if (postlist.get(idx).getCount() == postNum) return idx;
        }
        return -1;
    }

    public void searchPost(String keyword) {
        boolean success = false;
        System.out.println("==================");
        for (Post arr : postlist) {
            if (arr.getTitle().contains(keyword)) {
                printPost(arr);
                success = true;
            }
        }
        if (!success) System.out.println("검색된 게시물이 없습니다.");
    }

    public void printPosts() {
        System.out.println("==================");
        for (Post arr : postlist)
            printPost(arr);
    }

    public void printPost(Post post) {
        System.out.println(post.getCount() + ".");
        System.out.println("제목 : " + post.getTitle());
        System.out.println("==================");
    }

    public void printDetail(int idx) {
        System.out.println("==================");
        System.out.println(postlist.get(idx).getCount() + ".");
        System.out.println("제목 : " + postlist.get(idx).getTitle());
        System.out.println("내용 : " + postlist.get(idx).getDetail());
        System.out.println("등록날짜 : " + postlist.get(idx).getTime());
        System.out.println("조회수 : " + postlist.get(idx).getViews());
        System.out.println("추천수 : " + postlist.get(idx).getGreat());
        System.out.println("========댓글=======");
        postlist.get(idx).showComments();
    }

    public void setPost(String title, String detail) {
        Post post = new Post();
        post.setTitle(title);
        post.setDetail(detail);
        if (postlist.isEmpty()) post.setCount(1);
        else post.setCount(postlist.get(postlist.size() - 1).getCount() + 1);
        LocalDateTime now = LocalDateTime.now();
        post.setTime(now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss")));
        postlist.add(post);
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
}
