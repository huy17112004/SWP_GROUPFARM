package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplateUtil {
    /**
     * Đọc template HTML từ src/main/resources/templates/welcome.html
     * và thay các placeholder {{username}}, {{verifyUrl}}.
     *
     * @param resourcePath đường dẫn tương đối trên classpath, ví dụ "templates/welcome.html"
     * @param vars         map biến để replace
     */
    public static String render(String resourcePath, Map<String,String> vars) throws IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (InputStream is = cl.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Template không tìm thấy: " + resourcePath);
            }
            String html = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            for (Map.Entry<String,String> e : vars.entrySet()) {
                html = html.replace("{{" + e.getKey() + "}}", e.getValue());
            }
            return html;
        }
    }
}
