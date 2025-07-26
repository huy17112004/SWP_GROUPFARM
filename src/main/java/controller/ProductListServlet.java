package controller;

import com.google.gson.Gson;
import dto.ProductCreateDTO;
import dto.ProductListRequestDTO;
import dto.ProductListResponseDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import service.ProductListService;
import service.ProductImageService;
import entity.Product;
import entity.ProductImage;
import entity.Category;
import util.JpaUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import jakarta.servlet.http.Part;


@WebServlet("/api/products-list/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class ProductListServlet extends HttpServlet {
    private final ProductListService service = new ProductListService();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        resp.setContentType("application/json;charset=UTF-8");
        String path = req.getPathInfo();

        if (path != null && path.length() > 1) {
            // Chi tiết
            int id = Integer.parseInt(path.substring(1));
            ProductListResponseDTO dto = service.getProductDTOById(id);
            if (dto == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.getWriter().write(gson.toJson(dto));
        } else {
            // Danh sách
            String search = req.getParameter("search");
            String cat    = req.getParameter("categoryId");
            Integer categoryId = cat != null ? Integer.parseInt(cat) : null;

            List<ProductListResponseDTO> dtos = service.getAllProducts(search, categoryId);
            resp.getWriter().write(gson.toJson(dtos));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            // Parse form data
            String productName = req.getParameter("productName");
            int entryPrice = Integer.parseInt(req.getParameter("entryPrice"));
            int retailPrice = Integer.parseInt(req.getParameter("retailPrice"));
            BigDecimal wholesalePrice = new BigDecimal(req.getParameter("wholesalePrice"));
            String description = req.getParameter("description");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            
            // Validate required fields
            if (productName == null || productName.trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Tên sản phẩm không được để trống\"}");
                return;
            }
            
            // Get category
            Category category = em.find(Category.class, categoryId);
            if (category == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Category không tồn tại\"}");
                return;
            }
            
            // Create product
            Product product = new Product();
            product.setProductName(productName);
            product.setEntryPrice(entryPrice);
            product.setRetailPrice(retailPrice);
            product.setWholesalePrice(wholesalePrice);
            product.setDescription(description);
            product.setCategory(category);
            product.setImages(new ArrayList<>());
            
            // Persist product to get ID
            em.persist(product);
            em.flush(); // Force flush to get the ID
            
            // Handle image uploads
            List<Part> imageParts = req.getParts().stream()
                .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
                .toList();
            
            if (imageParts.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Cần ít nhất 1 ảnh\"}");
                return;
            }
            
            // Create upload directory if not exists
            String uploadPath = "D:/Learning/SWP/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // Process each image
            ProductImageService productImageService = new ProductImageService();
            
            for (int i = 0; i < imageParts.size(); i++) {
                Part imagePart = imageParts.get(i);
                String originalFileName = getSubmittedFileName(imagePart);
                String fileExtension = getFileExtension(originalFileName);
                String fileName = "product_" + product.getId() + "_" + (i + 1) + "." + fileExtension;
                String filePath = uploadPath + File.separator + fileName;
                
                // Save image as original format
                try (InputStream input = imagePart.getInputStream();
                     FileOutputStream output = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }
                
                // Create ProductImage entity
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl("/images/" + fileName);
                productImage.setProduct(product);
                
                // Save to database using ProductImageService trong cùng transaction
                productImageService.saveInTransaction(productImage, em);
            }
            
            tx.commit();
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"status\":\"success\",\"id\":" + product.getId() + "}");
            
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            
            // Chuyển stacktrace thành String
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String stack = sw.toString();

            // Trả về message + stacktrace cho client
            resp.getWriter().write("{"
                    + "\"error\":\"Lỗi hệ thống\","
                    + "\"message\":\"" + ex.getMessage() + "\","
                    + "\"stack\":\"" + stack.replace("\n", "\\n").replace("\r", "").replace("\"", "\\\"") + "\""
                    + "}");
            ex.printStackTrace(); // vẫn in ra log server cho chắc
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        resp.setContentType("application/json;charset=UTF-8");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        ProductListRequestDTO rq = gson.fromJson(req.getReader(), ProductListRequestDTO.class);
        ProductListResponseDTO dto = service.updateProduct(id, rq);
        if (dto == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.getWriter().write(gson.toJson(dto));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin",  "*");
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        boolean ok = service.deleteProduct(id);
        resp.setStatus(ok
                ? HttpServletResponse.SC_NO_CONTENT
                : HttpServletResponse.SC_NOT_FOUND);
    }
    
    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "jpg"; // default extension
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "jpg"; // default extension
    }
    

}
