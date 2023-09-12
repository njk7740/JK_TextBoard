import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {
    private String detail;
    private String time;

    Comment(String detail) {
        this.detail = detail;
        LocalDateTime now = LocalDateTime.now();
        this.time = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss"));
    }

    public void show() {
        System.out.println("댓글 내용 : " + detail);
        System.out.println("댓글 작성일 : " + time);
        System.out.println("==================");
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
