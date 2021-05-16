package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by fei on 2019/11/24.
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 分页查询
     * 先输入以下网址进行查询数据的测试，然后再写前端代码
     * http://localhost/travel/route/pageQuery?cid=5&&currentPage=23
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");//"null"

        // 接收rname 线路名称
        String rname = request.getParameter("rname");
        // 解决乱码
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        // 2.处理参数
        int cid = 0;//类别id
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            //
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;// 当前页码，如果不传递当前页码，则默认为第一页
        if(currentPageStr != null && currentPageStr.length() > 0){
            //
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;// 每页显示条数，如果不传递，默认每页显示5条记录
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            //
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //3.调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);

        //4.将PageBean对象序列化为json，返回
        writeValue(pb,response);

    }

    /**
     * 根据rid查询一个旅游线路的详细信息
     * 先输入以下网址进行查询数据的测试，然后再写前端代码
     * http://localhost/travel/route/findOne?rid=1
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收参数
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        //3.转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取线路id：rid
        String rid = request.getParameter("rid");
        //2.获取当前登录的用户 user
        User user = (User)request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            // 用户尚未登录
            uid = 0;
        }else{
            // 用户已经登录
            uid = user.getUid();
        }
        //3.调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid,uid);

        // 4.写回客户端
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取线路id：rid
        String rid = request.getParameter("rid");
        //2.获取当前登录的用户 user
        User user = (User)request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            // 用户尚未登录
            return;
        }else{
            // 用户已经登录
            uid = user.getUid();
        }

        //3.调用FavoriteService添加收藏
        // 3.1添加到favorite中
        favoriteService.add(rid,uid);

        // 3.2添加到route中
        favoriteService.addCount(rid);

    }

    /**
     * 我的收藏
     * http://localhost/travel/route/myFavorite
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取当前登录的用户 user
        User user = (User)request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            // 用户尚未登录
            return;
        }else{
            // 用户已经登录
            uid = user.getUid();
        }
        //2.调用FavoriteService查找用户的收藏线路
        // 2.1 通过uid查找rid
        List<Route> routeList = favoriteService.findByUid(uid);

        // 4.写回客户端
        writeValue(routeList, response);
    }

}
