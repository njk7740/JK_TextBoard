import java.util.Comparator;

class PostTitleCompare implements Comparator<Post> {
    public int compare(Post a, Post b) {
        return a.getTitle().compareTo(b.getTitle());
    }
}

class PostViewsCompare implements Comparator<Post> {
    public int compare(Post a, Post b) {
        if (a.getViews() > b.getViews()) return 1;
        if (b.getViews() > a.getViews()) return -1;
        return 0;
    }
}

class PostTimeCompare implements Comparator<Post> {
    public int compare(Post a, Post b) {
        return a.getTime().compareTo(b.getTime());
    }
}