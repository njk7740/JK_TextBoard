import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Post {
    private String title;
    private String detail;
    private int count;
    private int views;
    private int great;
    private String time;
    private ArrayList<Integer> greatPeople;
    private ArrayList<Comment> commentlist;
    private int writterIdx;
    private String writter;
    Scanner sc;

    Post() {
        views = 0;
        great = 0;
        commentlist = new ArrayList<>();
        greatPeople = new ArrayList<>();
        writterIdx = 0;
        writter = "";
    }

    public void greatUp(int userIdx) {
        if (greatPeople.isEmpty()) {
            great++;
            greatPeople.add(userIdx);
        } else {
            for (Integer i : greatPeople) {
                if (i.equals(userIdx)) {
                    System.out.println("이미 추천한 게시물입니다.");
                    return;
                }
            }
            great++;
            greatPeople.add(userIdx);
        }
        System.out.println("해당 게시물을 추천했습니다.");
    }

    public int getGreat() {
        return great;
    }

    public void setGreat(int great) {
        this.great = great;
    }

    public void addComment() {
        sc = new Scanner(System.in);
        System.out.print("댓글 내용 : ");
        Comment comment = new Comment(sc.nextLine());
        commentlist.add(comment);
    }

    public void showComments() {
        if (commentlist.isEmpty()) {
            System.out.println("없음");
            System.out.println("==================");
        } else {
            for (Comment arr : commentlist)
                arr.show();
        }
    }

    public void save(PrintWriter pw) {
        pw.println(greatPeople.size());
        for (Integer i : greatPeople) {
            pw.println(i);
        }
        pw.println(commentlist.size());
        for (Comment comment : commentlist) {
            pw.println(comment.getDetail());
            pw.println(comment.getTime());
        }
    }

    public void load(BufferedReader br) {
        try {
            int gpSize = Integer.parseInt(br.readLine());
            for (int i = 0; i < gpSize; i++) {
                greatPeople.add(Integer.parseInt(br.readLine()));
            }
            int clSize = Integer.parseInt(br.readLine());
            for (int i = 0; i < clSize; i++) {
                Comment comment = new Comment(br.readLine());
                comment.setTime(br.readLine());
                commentlist.add(comment);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewUp() {
        views++;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public int getCount() {
        return count;
    }

    public String getWritter() {
        return writter;
    }

    public int getWritterIdx() {
        return writterIdx;
    }

    public void setWritterIdx(int writterIdx) {
        this.writterIdx = writterIdx;
    }

    public void setWritter(String writter) {
        this.writter = writter;
    }
}
