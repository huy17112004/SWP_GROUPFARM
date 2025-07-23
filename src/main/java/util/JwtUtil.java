package util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    // Những biến sẽ được nạp từ AppConfigListener
    public static String SECRET;
    public static long   EXP_MS;

    private static Key key;
    private static boolean initialized = false;

    private static synchronized void init() {
        if (initialized) return;
        if (SECRET == null || SECRET.isEmpty()) {
            throw new IllegalStateException("Missing JWT_SECRET");
        }
        if (EXP_MS <= 0) {
            throw new IllegalStateException("Missing or invalid JWT_EXP_MS");
        }
        byte[] secretBytes = Decoders.BASE64.decode(SECRET);
        key = Keys.hmacShaKeyFor(secretBytes);
        initialized = true;
    }

    public static String generateEmailToken(String email) {
        init();
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXP_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String validateAndGetEmail(String token) {
        init();
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public static String generatePasswordResetToken(String email) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXP_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static String validateAndGetEmailFromResetToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Link reset mật khẩu không hợp lệ hoặc đã hết hạn");
        }
    }
}
