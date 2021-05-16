package cn.itcast.travel.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 收藏实体类
 */
public class Favorite implements Serializable {
    private int rid;
    private Route route;//旅游线路对象
    private String date;//收藏时间
    private User user;//所属用户

    /**
     * 无参构造方法
     */
    public Favorite() {
    }

    /**
     * 有参构造方法
     * @param route
     * @param date
     * @param user
     */
    public Favorite(int rid, Route route, String date, User user) {
        this.rid = rid;
        this.route = route;
        this.date = date;
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    @Override
    public String toString() {
        return "tab_favorite{" +
                "rid=" + rid +
                ", date=" + date +
                ", user='" + user + '\'' +
                '}';
    }
}
