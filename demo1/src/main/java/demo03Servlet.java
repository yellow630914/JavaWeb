import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//request.getSession() -> 獲取當前session,沒有則創建一個新的會話
//request.getSession(false) -> 獲取當前session,沒有不會創建新的

//session.getId() -> 獲取sessionID
//session.isNew() -> 判斷當前session是否為新
//session.getMaxInactiveInterval() -> session的存活時間
//session.invalidate() -> 強制性讓會話失效

public class demo03Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println(session);
    }
}
