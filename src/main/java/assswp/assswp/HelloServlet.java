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

        session.getTransaction().commit();
        session.close();

        response.getWriter().println("✅ Hibernate session ran. Check DB for 'Account' table.");
    }

    public void destroy() {
    }
}