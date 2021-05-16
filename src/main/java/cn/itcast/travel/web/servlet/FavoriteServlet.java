package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fei on 2019/11/28.
 */
@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    FavoriteService favoriteService=new FavoriteServiceImpl();

    /**
     * http://localhost/travel/favorite/favoriteOrder?highPrice=20000&lowPrice=10&cname=西安
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid="";
        String currentPage = request.getParameter("currentPage");
        String cname = request.getParameter("cname");
        // 解决乱码
        cname = new String(cname.getBytes("iso-8859-1"),"utf-8");
        System.out.println("cname"+cname);
        //
        String highPrice = request.getParameter("highPrice");
        String lowPrice = request.getParameter("lowPrice");
        String pageSize="8";
        //
        PageBean pageBean=favoriteService.findRouteOrderPageBean(cid,currentPage,cname,highPrice,lowPrice,pageSize);
        //
        writeValue(pageBean,response);
    }
}