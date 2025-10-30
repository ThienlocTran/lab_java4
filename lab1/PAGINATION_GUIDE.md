# Hướng dẫn Phân Trang (Pagination) Users

## Mô tả
Chương trình phân trang để hiển thị danh sách users ở trang thứ 3 với kích thước trang là 5 users.

## Cách hoạt động

### 1. Console Output (Tự động chạy khi khởi động)
Khi ứng dụng Spring Boot khởi động, `UserPaginationConsole` sẽ tự động chạy và hiển thị trang thứ 3 trên console.

**Output ví dụ:**
```
========================================
CHƯƠNG TRÌNH PHÂN TRANG USERS
========================================

Tổng số users: 15
Kích thước trang: 5
Tổng số trang: 3

Hiển thị trang thứ 3:

Hiển thị users từ vị trí 11 đến 15

┌─────┬─────────────────────────┬──────────────────────────┬─────────┐
│ STT │ Họ Tên                  │ Email                    │ Admin   │
├─────┼─────────────────────────┼──────────────────────────┼─────────┤
│  11 │ Nguyễn Văn K            │ k@gmail.com              │ No      │
│  12 │ Trần Thị L              │ l@gmail.com              │ No      │
│  13 │ Lê Văn M                │ m@gmail.com              │ Yes     │
│  14 │ Phạm Thị N              │ n@gmail.com              │ No      │
│  15 │ Hoàng Văn O             │ o@gmail.com              │ No      │
└─────┴─────────────────────────┴──────────────────────────┴─────────┘

========================================
```

### 2. REST API Endpoint
Bạn cũng có thể gọi API để lấy dữ liệu phân trang:

```
GET /api/users/page?pageNumber=2&pageSize=5
```

**Parameters:**
- `pageNumber`: Số trang (0-indexed, mặc định 0)
- `pageSize`: Số users mỗi trang (mặc định 5)

**Response ví dụ:**
```json
{
  "pageNumber": 2,
  "pageSize": 5,
  "totalUsers": 15,
  "totalPages": 3,
  "users": [
    {
      "id": 11,
      "password": "123456",
      "fullname": "Nguyễn Văn K",
      "email": "k@gmail.com",
      "admin": false
    },
    {
      "id": 12,
      "password": "123456",
      "fullname": "Trần Thị L",
      "email": "l@gmail.com",
      "admin": false
    },
    ...
  ]
}
```

## Cấu trúc Code

### UserService - Phương thức phân trang

```java
// Lấy users của một trang cụ thể
public List<User> findUsersByPage(int pageNumber, int pageSize) {
    String jpql = "SELECT o FROM User o";
    TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
    query.setFirstResult(pageNumber * pageSize);  // Vị trí bắt đầu
    query.setMaxResults(pageSize);                 // Số phần tử tối đa
    return query.getResultList();
}

// Lấy tổng số users
public long getTotalUserCount() {
    String jpql = "SELECT COUNT(o) FROM User o";
    return entityManager.createQuery(jpql, Long.class).getSingleResult();
}
```

**Giải thích:**
- `query.setFirstResult(pageNumber * pageSize)` - Chỉ định vị trí bắt đầu
  - Trang 1 (pageNumber=0): `0 * 5 = 0` (bắt đầu từ user 1)
  - Trang 2 (pageNumber=1): `1 * 5 = 5` (bắt đầu từ user 6)
  - Trang 3 (pageNumber=2): `2 * 5 = 10` (bắt đầu từ user 11)
- `query.setMaxResults(pageSize)` - Số phần tử tối đa trả về (5)

### UserPaginationConsole - CommandLineRunner

```java
@Component
public class UserPaginationConsole implements CommandLineRunner {
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUMBER = 2; // Trang thứ 3
    
    @Override
    public void run(String ... args) throws Exception {
        List<User> users = userService.findUsersByPage(PAGE_NUMBER, PAGE_SIZE);
        // Hiển thị kết quả
    }
}
```

### UserController - REST Endpoint

```java
@GetMapping("/page")
public ResponseEntity<?> getUsersByPage(
        @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
    
    List<User> users = userService.findUsersByPage(pageNumber, pageSize);
    long totalCount = userService.getTotalUserCount();
    long totalPages = (totalCount + pageSize - 1) / pageSize;
    
    return ResponseEntity.ok(new HashMap<String, Object>() {{
        put("pageNumber", pageNumber);
        put("pageSize", pageSize);
        put("totalUsers", totalCount);
        put("totalPages", totalPages);
        put("users", users);
    }});
}
```

## Công thức tính toán

### Vị trí bắt đầu (FirstResult)
```
firstResult = pageNumber * pageSize
```

Ví dụ với pageSize = 5:
- Trang 1: firstResult = 0 * 5 = 0 (users 1-5)
- Trang 2: firstResult = 1 * 5 = 5 (users 6-10)
- Trang 3: firstResult = 2 * 5 = 10 (users 11-15)

### Tổng số trang
```
totalPages = (totalUsers + pageSize - 1) / pageSize
```

Ví dụ:
- 15 users, pageSize 5: (15 + 5 - 1) / 5 = 19 / 5 = 3 trang
- 16 users, pageSize 5: (16 + 5 - 1) / 5 = 20 / 5 = 4 trang
- 14 users, pageSize 5: (14 + 5 - 1) / 5 = 18 / 5 = 3 trang

## Dữ liệu Test

Để test chương trình, chèn ít nhất 15 users vào database:

```sql
INSERT INTO Users (password, fullname, email, admin) VALUES
('123456', 'Nguyễn Văn A', 'a@gmail.com', 0),
('123456', 'Trần Thị B', 'b@gmail.com', 0),
('123456', 'Lê Văn C', 'c@gmail.com', 0),
('123456', 'Phạm Thị D', 'd@gmail.com', 0),
('123456', 'Hoàng Văn E', 'e@gmail.com', 1),
('123456', 'Nguyễn Thị F', 'f@gmail.com', 0),
('123456', 'Trần Văn G', 'g@gmail.com', 0),
('123456', 'Lê Thị H', 'h@gmail.com', 0),
('123456', 'Phạm Văn I', 'i@gmail.com', 0),
('123456', 'Hoàng Thị J', 'j@gmail.com', 1),
('123456', 'Nguyễn Văn K', 'k@gmail.com', 0),
('123456', 'Trần Thị L', 'l@gmail.com', 0),
('123456', 'Lê Văn M', 'm@gmail.com', 1),
('123456', 'Phạm Thị N', 'n@gmail.com', 0),
('123456', 'Hoàng Văn O', 'o@gmail.com', 0);
```

## Chạy ứng dụng

```bash
mvn spring-boot:run
```

Kết quả phân trang sẽ hiển thị tự động trên console.

## Test API

### Lấy trang 1 (5 users đầu tiên)
```bash
curl "http://localhost:8080/api/users/page?pageNumber=0&pageSize=5"
```

### Lấy trang 2 (5 users tiếp theo)
```bash
curl "http://localhost:8080/api/users/page?pageNumber=1&pageSize=5"
```

### Lấy trang 3 (5 users cuối cùng)
```bash
curl "http://localhost:8080/api/users/page?pageNumber=2&pageSize=5"
```

### Lấy trang 1 với kích thước trang 10
```bash
curl "http://localhost:8080/api/users/page?pageNumber=0&pageSize=10"
```

## Lợi ích của Pagination

1. **Tiết kiệm tài nguyên** - Không tải toàn bộ dữ liệu
2. **Cải thiện hiệu suất** - Truy vấn nhanh hơn
3. **Tốt cho UX** - Dễ dàng điều hướng
4. **Scalable** - Hoạt động tốt với dữ liệu lớn

## Mở rộng

Bạn có thể tạo thêm các phương thức phân trang khác:

```java
// Phân trang với sắp xếp
public List<User> findUsersByPageSorted(int pageNumber, int pageSize, String orderBy) {
    String jpql = "SELECT o FROM User o ORDER BY o." + orderBy;
    TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
    query.setFirstResult(pageNumber * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
}

// Phân trang với điều kiện
public List<User> findAdminUsersByPage(int pageNumber, int pageSize) {
    String jpql = "SELECT o FROM User o WHERE o.admin = true";
    TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
    query.setFirstResult(pageNumber * pageSize);
    query.setMaxResults(pageSize);
    return query.getResultList();
}
```
