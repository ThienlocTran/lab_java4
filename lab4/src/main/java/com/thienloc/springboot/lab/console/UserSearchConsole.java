package com.thienloc.springboot.lab.console;

import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserSearchConsole implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String ... args) throws Exception {
        System.out.println("\n========================================");
        System.out.println("CHƯƠNG TRÌNH TÌM KIẾM USERS");
        System.out.println("========================================\n");
        
        // Tìm users có email kết thúc bằng "@fpt.edu.vn" và không phải admin
        System.out.println("Tìm kiếm users có email kết thúc bằng '@fpt.edu.vn' và không phải admin:\n");
        
        List<User> users = userService.findFptEmailNonAdminUsers();
        
        if (users.isEmpty()) {
            System.out.println("Không tìm thấy user nào phù hợp!");
        } else {
            System.out.println("Kết quả tìm kiếm: " + users.size() + " user(s) tìm thấy\n");
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ STT │ Họ Tên                  │ Email                    │");
            System.out.println("├─────┼─────────────────────────┼──────────────────────────┤");
            
            int stt = 1;
            for (User user : users) {
                String fullname = user.getFullname();
                String email = user.getEmail();
                
                // Định dạng cột
                String sttStr = String.format("%3d", stt);
                String fullnameStr = String.format("%-23s", fullname != null ? fullname : "N/A");
                String emailStr = String.format("%-24s", email != null ? email : "N/A");
                
                System.out.printf("│ %s │ %s │ %s │\n", sttStr, fullnameStr, emailStr);
                stt++;
            }
            
            System.out.println("└─────┴─────────────────────────┴──────────────────────────┘");
        }
        
        System.out.println("\n========================================\n");
    }
}
