package controller;

import entity.User;
import service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class UserControllerServlet
 * Handles user management functions including:
 * - View all users
 * - Add new user
 * - Edit user information
 * - Delete user
 * - Search and filter users
 */
@WebServlet(name = "UserControllerServlet", urlPatterns = {"/admin/users", "/admin/users/*"})
public class UserControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserControllerServlet() {
        super();
        userService = new UserService();
    }

    @Override
    public void destroy() {
        if (userService != null) {
            userService.close();
        }
        super.destroy();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * Handles GET requests for user management
     * - /admin/users - View all users
     * - /admin/users/add - Show add user form
     * - /admin/users/edit - Show edit user form
     * - /admin/users/view - Show user details
     * - /admin/users/search - Search users
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listUsers(request, response);
                break;
            case "/add":
                showAddUserForm(request, response);
                break;
            case "/edit":
                showEditUserForm(request, response);
                break;
            case "/view":
                viewUserDetails(request, response);
                break;
            case "/search":
                searchUsers(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Handles POST requests for user management
     * - /admin/users/add - Process add user form
     * - /admin/users/edit - Process edit user form
     * - /admin/users/delete - Process user deletion
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/add":
                addUser(request, response);
                break;
            case "/edit":
                updateUser(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/users");
                break;
        }
    }

    /**
     * List all users
     * @param request
     * @param response
     */
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/back-end/all-users.jsp").forward(request, response);
    }

    /**
     * Show form to add a new user
     */
    private void showAddUserForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/back-end/add-new-user.jsp").forward(request, response);
    }

    /**
     * Show form to edit an existing user
     */
    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter("id"));
            User user = userService.getUserById(userId);

            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/back-end/edit-user.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "User not found");
                listUsers(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID");
            listUsers(request, response);
        }
    }

    /**
     * View user details
     */
    private void viewUserDetails(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter("id"));
            User user = userService.getUserById(userId);

            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/back-end/user-detail.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "User not found");
                listUsers(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID");
            listUsers(request, response);
        }
    }

    /**
     * Add a new user
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        // Validate form data
        if (!validateUserData(username, password, email, phone)) {
            request.setAttribute("errorMessage", "Please fill all required fields with valid data");
            request.getRequestDispatcher("/back-end/add-new-user.jsp").forward(request, response);
            return;
        }

        try {
            // Create a new user object
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setPhone(phone);
            user.setEmail(email);
            user.setAddress(address);
            user.setActive(true);

            // Save the user
            userService.createUser(user);

            // Set success message and redirect
            request.getSession().setAttribute("successMessage", "User added successfully");
            response.sendRedirect(request.getContextPath() + "/admin/users");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/back-end/add-new-user.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while saving the user");
            request.getRequestDispatcher("/back-end/add-new-user.jsp").forward(request, response);
        }
    }

    /**
     * Update an existing user
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get form parameters
            long userId = Long.parseLong(request.getParameter("id"));
            String username = request.getParameter("username");
            String password = request.getParameter("password"); // May be empty if not changing
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");

            // Get existing user
            User user = userService.getUserById(userId);
            if (user == null) {
                request.setAttribute("errorMessage", "User not found");
                listUsers(request, response);
                return;
            }

            // Validate basic data
            if (username == null || username.trim().isEmpty() ||
                email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") ||
                phone == null || !phone.matches("\\+?[0-9\\s-]{10,}")) {

                request.setAttribute("errorMessage", "Please fill all required fields with valid data");
                request.setAttribute("user", user);
                request.getRequestDispatcher("/back-end/edit-user.jsp").forward(request, response);
                return;
            }

            // Update user object
            user.setUsername(username);
            // Only update password if provided
            if (password != null && !password.trim().isEmpty()) {
                if (password.length() < 6) {
                    request.setAttribute("errorMessage", "Password must be at least 6 characters");
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/back-end/edit-user.jsp").forward(request, response);
                    return;
                }
                user.setPassword(password);
            }
            user.setFullName(fullName);
            user.setPhone(phone);
            user.setEmail(email);
            user.setAddress(address);

            // Update the user
            userService.updateUser(user);

            // Set success message and redirect
            request.getSession().setAttribute("successMessage", "User updated successfully");
            response.sendRedirect(request.getContextPath() + "/admin/users");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID");
            listUsers(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while updating the user");
            listUsers(request, response);
        }
    }

    /**
     * Delete a user
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            long userId = Long.parseLong(request.getParameter("id"));
            boolean deleted = userService.deleteUser(userId);

            if (deleted) {
                request.getSession().setAttribute("successMessage", "User deleted successfully");
            } else {
                request.getSession().setAttribute("errorMessage", "User could not be deleted");
            }

            response.sendRedirect(request.getContextPath() + "/admin/users");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid user ID");
            listUsers(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while deleting the user");
            listUsers(request, response);
        }
    }

    /**
     * Search and filter users
     */
    private void searchUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String searchTerm = request.getParameter("q");

        List<User> users = userService.searchUsers(searchTerm);

        request.setAttribute("users", users);
        request.setAttribute("searchTerm", searchTerm);
        request.getRequestDispatcher("/back-end/all-users.jsp").forward(request, response);
    }

    /**
     * Validate user data
     * @return true if data is valid, false otherwise
     */
    private boolean validateUserData(String username, String password, String email, String phone) {
        // Basic validation
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.length() < 6) {
            return false;
        }
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return false;
        }
        if (phone == null || !phone.matches("\\+?[0-9\\s-]{10,}")) {
            return false;
        }
        return true;
    }
}
