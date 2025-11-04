# Spring Data JPA JPQL Guide - Lab 4

## Cách viết JPQL bằng Spring Data JPA

Spring Data JPA cung cấp hai cách để viết JPQL queries:

### 1. Query Methods (Recommended)
Spring Data JPA tự động generate JPQL queries dựa trên tên method.

### 2. Custom @Query Annotation
Viết JPQL query tường minh bằng `@Query` annotation.

---

## Ví dụ Thực Tế

### UserRepository

#### Query Method - Tìm User theo email
```java
// Spring Data JPA tự động generate:
// SELECT u FROM User u WHERE u.email LIKE %:email%
Optional<User> findByEmailContaining(String email);
```

#### Query Method - Tìm User có role admin
```java
// Spring Data JPA tự động generate:
// SELECT u FROM User u WHERE u.admin = true
List<User> findByAdmin(Boolean admin);
```

#### Query Method - Tìm User theo email chứa keyword và không phải admin
```java
// Spring Data JPA tự động generate:
// SELECT u FROM User u WHERE u.email LIKE %:email% AND u.admin = false
List<User> findByEmailContainingAndAdminFalse(String email);
```

#### Custom @Query - Tìm tất cả User
```java
// Custom JPQL query
@Query("SELECT u FROM User u")
List<User> findAllUsers();
```

---

### VideoRepository

#### Query Method - Tìm Video theo title
```java
// Spring Data JPA tự động generate:
// SELECT v FROM Video v WHERE v.title LIKE %:title%
List<Video> findByTitleContaining(String title);
```

#### Query Method - Tìm Video không bị xóa
```java
// Spring Data JPA tự động generate:
// SELECT v FROM Video v WHERE v.active = true
List<Video> findByActiveTrue();
```

#### Query Method - Tìm Video có lượt xem lớn hơn
```java
// Spring Data JPA tự động generate:
// SELECT v FROM Video v WHERE v.views > :views
List<Video> findByViewsGreaterThan(Integer views);
```

#### Query Method - Tìm Video sắp xếp theo lượt xem
```java
// Spring Data JPA tự động generate:
// SELECT v FROM Video v ORDER BY v.views DESC
List<Video> findAll(Sort sort);

// Sử dụng trong Service:
videoRepository.findAll(Sort.by(Sort.Direction.DESC, "views"));
```

---

### FavoriteRepository

#### Query Method - Tìm Favorite của User
```java
// Spring Data JPA tự động generate:
// SELECT f FROM Favorite f WHERE f.user.id = :userId
List<Favorite> findByUserId(Long userId);
```

#### Query Method - Tìm Favorite của Video
```java
// Spring Data JPA tự động generate:
// SELECT f FROM Favorite f WHERE f.video.id = :videoId
List<Favorite> findByVideoId(Long videoId);
```

#### Query Method - Tìm Favorite theo User và Video
```java
// Spring Data JPA tự động generate:
// SELECT f FROM Favorite f WHERE f.user = :user AND f.video = :video
List<Favorite> findByUserAndVideo(User user, Video video);
```

---

## Spring Data JPA Query Method Naming Convention

| Method Keyword | JPQL Equivalent | Example |
|---|---|---|
| `findBy` | WHERE | `findByEmail()` → WHERE email = ? |
| `Containing` | LIKE | `findByEmailContaining()` → WHERE email LIKE %?% |
| `And` | AND | `findByEmailAndAdmin()` → WHERE email AND admin |
| `Or` | OR | `findByEmailOrUsername()` → WHERE email OR username |
| `GreaterThan` | > | `findByViewsGreaterThan()` → WHERE views > ? |
| `LessThan` | < | `findByViewsLessThan()` → WHERE views < ? |
| `Between` | BETWEEN | `findByViewsBetween()` → WHERE views BETWEEN ? AND ? |
| `True` | = true | `findByActiveTrue()` → WHERE active = true |
| `False` | = false | `findByActiveFalse()` → WHERE active = false |
| `OrderBy` | ORDER BY | `findAllOrderByViews()` → ORDER BY views |

---

## Sử Dụng trong Service

```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Sử dụng Query Method
    public List<User> findFptEmailNonAdminUsers() {
        return userRepository.findByEmailContainingAndAdminFalse("fpt");
    }
    
    // Sử dụng Query Method
    public List<User> findAdminUsers() {
        return userRepository.findByAdmin(true);
    }
    
    // Sử dụng Custom @Query
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
```

---

## Lợi Ích của Spring Data JPA Query Methods

✅ **Không cần viết JPQL** - Spring tự động generate  
✅ **Type-safe** - Compile-time checking  
✅ **Dễ đọc** - Tên method rõ ràng  
✅ **Dễ bảo trì** - Thay đổi logic dễ dàng  
✅ **Hỗ trợ Pagination & Sorting** - Tích hợp sẵn  

---

## Khi nào dùng @Query?

Dùng `@Query` khi:
- Query phức tạp không thể biểu diễn bằng method naming
- Cần tối ưu performance với custom JPQL
- Cần sử dụng native SQL

Ví dụ:
```java
@Query("SELECT u FROM User u WHERE u.email LIKE :email AND u.admin = false ORDER BY u.id DESC")
List<User> findNonAdminUsersByEmail(@Param("email") String email);
```

---

## Tổng Kết

**Lab 4 sử dụng:**
- ✅ Spring Data JPA Query Methods (chính)
- ✅ Custom @Query cho queries phức tạp (bổ sung)
- ✅ Automatic JPQL generation
- ✅ Type-safe queries

**Không cần viết JPQL trực tiếp** - Spring Data JPA xử lý tất cả!
