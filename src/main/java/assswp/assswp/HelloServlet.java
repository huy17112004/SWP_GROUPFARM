package assswp.assswp;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.hibernate.Session;
import util.HibernateUtil;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Gọi đến entity để Hibernate map và tạo bảng
        entity.Account acc = new entity.Account();
        acc.setUsername("admin");
        acc.setPassword("123456");

        session.persist(acc);  // Bắt buộc phải persist thì Hibernate mới tạo bảng

        session.getTransaction().commit();
        session.close();

        response.getWriter().println("✅ Hibernate session ran. Check DB for 'Account' table.");
    }

    public void destroy() {
    }
}