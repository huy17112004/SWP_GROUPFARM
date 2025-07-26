package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    // Thay đổi đường dẫn thư mục này theo nơi bạn lưu ảnh
    private static final String UPLOAD_DIR = "D:/Learning/SWP/uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy tên file từ URL (VD: /images/product_5_1.jpg => "product_5_1.jpg")
        String requestedFile = req.getPathInfo();
        if (requestedFile == null || requestedFile.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Filename is required");
            return;
        }

        // Tránh truy cập vượt thư mục
        String safeFileName = requestedFile.replace("/", "").replace("..", "");
        File file = new File(UPLOAD_DIR, safeFileName);

        // Kiểm tra file tồn tại
        if (!file.exists()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("File not found");
            return;
        }

        // Thiết lập loại MIME
        String mime = getServletContext().getMimeType(file.getName());
        if (mime == null) {
            mime = "application/octet-stream";
        }

        resp.setContentType(mime);
        resp.setContentLengthLong(file.length());

        // Gửi file về client
        try (InputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}
