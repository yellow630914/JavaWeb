import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class demo02Servlet extends HttpServlet {
    public demo02Servlet(){
        System.out.println("正在實例化");
    }
    @Override
    public void init() throws ServletException {
        System.out.println("正在初始化");
    }
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("正在服務");
    }
    @Override
    public void destroy() {
        System.out.println("正在銷毀");
    }
}

