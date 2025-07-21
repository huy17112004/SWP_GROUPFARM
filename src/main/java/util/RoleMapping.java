//package util;
//
//import entity.Account;
//import entity.Admin;
//
//import java.util.Set;
//
//public class RoleMapping {
//
//    // Danh sách username hoặc id của admin hệ thống
//    private static final Set<String> ADMIN_SYSTEM_USERNAMES = Set.of("admin_master", "root");
//
//    public static boolean isAdminSystem(Account account) {
//        return account instanceof Admin && ADMIN_SYSTEM_USERNAMES.contains(account.getUsername());
//    }
//
//    public static boolean isAdminBusiness(Account account) {
//        return account instanceof Admin && !isAdminSystem(account);
//    }
//}
