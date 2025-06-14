package validation;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.JpaUtil;

public class AccountValidation {

    public static void validateAccountCreation(String userName, String email, String password, Class<?> entityType) throws IllegalArgumentException {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!userName.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Username can only contain letters and numbers");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        try (Session session = JpaUtil.getEntityManager().unwrap(Session.class)) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(a) FROM " + entityType.getSimpleName() + " a WHERE a.email = :email",
                    Long.class
            );
            query.setParameter("email", email);
            if (query.uniqueResult() > 0) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
    }
}