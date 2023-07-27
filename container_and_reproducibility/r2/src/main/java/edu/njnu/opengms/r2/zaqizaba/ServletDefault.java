package edu.njnu.opengms.r2.zaqizaba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ServletDefault
 * @Description todo
 * @Author sun_liber
 * @Date 2019/10/15
 * @Version 1.0.0
 */
@WebServlet ("/ServletDefault")
public class ServletDefault extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello World!");
    }
}
