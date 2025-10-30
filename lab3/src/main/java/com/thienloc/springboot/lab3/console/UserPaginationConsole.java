package com.thienloc.springboot.lab2.console;

import com.thienloc.springboot.lab2.entity.User;
import com.thienloc.springboot.lab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserPaginationConsole implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUMBER = 2; // Trang thứ 3 (0-indexed: 0=trang 1, 1=trang 2, 2=trang 3)
    
    @Override
    public void run(String ... args) throws Exception {
        System.out.println("\n========================================");
        System.out.println("CHƯƠNG TRÌNH PHÂN TRANG USERS");
        System.out.println("========================================\n");
        
        // Lấy tổng số users
        long totalUsers = userService.getTotalUserCount();
        long totalPages = (totalUsers + PAGE_SIZE - 1) / PAGE_SIZE;
        
        System.out.println("Tổng số users: " + totalUsers);
        System.out.println("Kích thước trang: " + PAGE_SIZE);
        System.out.println("Tổng số trang: " + totalPages);
        System.out.println("\nHiển thị trang thứ " + (PAGE_NUMBER + 1) + ":\n");
        
        // Lấy dữ liệu trang thứ 3
        List<User> users = userService.findUsersByPage(PAGE_NUMBER, PAGE_SIZE);
        
        if (users.isEmpty()) {
            System.out.println("Trang này không có dữ liệu!");
        } else {
            int startIndex = PAGE_NUMBER * PAGE_SIZE + 1;
            System.out.println("Hiển thị users từ vị trí " + startIndex + " đến " + (startIndex + users.size() - 1) + "\n");
            
            System.out.println("┌─────┬─────────────────────────┬──────────────────────────┬─────────┐");
            System.out.println("│ STT │ Họ Tên                  │ Email                    │ Admin   │");
            System.out.println("├─────┼─────────────────────────┼──────────────────────────┼─────────┤");
            
            int stt = startIndex;
            for (User user : users) {
                String fullname = user.getFullname() != null ? user.getFullname() : "N/A";
                String email = user.getEmail() != null ? user.getEmail() : "N/A";
                String admin = user.getAdmin() != null && user.getAdmin() ? "Yes" : "No";
                
                String sttStr = String.format("%3d", stt);
                String fullnameStr = String.format("%-23s", fullname);
                String emailStr = String.format("%-24s", email);
                String adminStr = String.format("%-7s", admin);
                
                System.out.printf("│ %s │ %s │ %s │ %s │\n", sttStr, fullnameStr, emailStr, adminStr);
                stt++;
            }
            
            System.out.println("└─────┴─────────────────────────┴──────────────────────────┴─────────┘");
        }
        
        System.out.println("\n========================================\n");
    }
}
