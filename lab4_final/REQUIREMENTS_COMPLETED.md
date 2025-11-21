# Lab4 Final - Spring Data JPA Requirements Completed

## Các Yêu Cầu Đã Hoàn Thành

### 1. Tìm User theo ID hoặc Email ✓
**Location**: `UserRepository.java` & `UserService.java`
- **Method**: `Optional<User> findByEmail(String email)` 
- **Implementation**: Spring Data JPA method findByEmail - email là unique field
- **Usage**: `userService.findByEmail("admin@fpt.edu.vn")`

### 2. Tìm Video theo Title chứa Keyword ✓
**Location**: `VideoRepository.java` & `VideoService.java`
- **Method**: `List<Video> findByTitleContainingIgnoreCase(String keyword)`
- **Implementation**: Spring Data JPA method - case insensitive search
- **Usage**: `videoService.findByTitleContaining("video")`
- **UI**: /test-query - Search Videos section

### 3. Truy Vấn 10 Video Được Yêu Thích Nhiều Nhất ✓
**Location**: `VideoRepository.java` & `VideoService.java`
- **Method**: `List<Video> findTop10MostFavorited()`
- **Implementation**: Native SQL query with GROUP BY and COUNT
- **Query**: 
  ```sql
  SELECT TOP 10 v.id, v.title, v.poster, v.views, v.description, v.active 
  FROM Videos v 
  LEFT JOIN Favorite f ON v.id = f.Videold 
  GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active 
  ORDER BY COUNT(f.id) DESC
  ```
- **UI**: /test-query - Top 10 Videos section

### 4. Tìm Video Không Được Ai Thích ✓
**Location**: `VideoRepository.java` & `VideoService.java`
- **Method**: `List<Video> findVideosWithoutFavorites()`
- **Implementation**: JPQL query using SIZE() function
- **Query**: `SELECT v FROM Video v WHERE SIZE(v.favorites) = 0 ORDER BY v.id`
- **UI**: /test-query - Videos without Favorites section

### 5. Tìm Video Được Chia Sẻ trong Năm 2024 & Sắp Xếp theo Thời Gian ✓
**Location**: `ShareRepository.java` & `ShareService.java`
- **Method**: `List<Share> findSharedVideosIn2024()`
- **Implementation**: JPQL query using YEAR() function
- **Query**: `SELECT s FROM Share s WHERE YEAR(s.shareDate) = 2024 ORDER BY s.shareDate DESC`
- **UI**: /test-query - Videos Shared in 2024 section

### 6. Xây Dựng Trang Web Hiển Thị Share Summary ✓
**Location**: `ShareSummaryController.java` & `share-summary.html`
- **Endpoint**: `/share-summary`
- **Features**:
  - Hiển thị danh sách tất cả các video đã được chia sẻ
  - Hiển thị số lần share của mỗi video
  - Hiển thị danh sách email người nhận chia sẻ
  - Hiển thị ngày chia sẻ gần nhất
  - Poster/thumbnail của video
  - Sắp xếp theo thời gian chia sẻ (DESC)
- **DTO**: `ShareSummaryDto` - chứa thông tin tóm tắt

## Thêm - API Endpoints

### REST API Endpoints (QueryController)
Tất cả các queries cũng có REST API endpoints để dễ test:

- `GET /api/query/user/email/{email}` - Tìm user theo email
- `GET /api/query/user/id/{id}` - Tìm user theo ID
- `GET /api/query/video/search?keyword=xxx` - Tìm video theo keyword
- `GET /api/query/video/top10-favorited` - 10 video yêu thích nhất
- `GET /api/query/video/no-favorites` - Video không có favorite
- `GET /api/query/share/2024` - Video chia sẻ trong 2024
- `GET /api/query/share/video/{videoId}` - Share info của video

## Test Pages & Console

### UI Test Pages:
1. `/test-query` - Interactive query testing UI
2. `/share-summary` - Share summary display

### Console Output:
- `VideoQueryConsole` - Tự động in ra kết quả test khi ứng dụng startup

## Các Files Được Tạo/Sửa

### New Files:
- `ShareSummaryController.java`
- `QueryController.java`
- `TestQueryController.java`
- `VideoQueryConsole.java`
- `ShareSummaryDto.java`
- `share-summary.html`
- `test-query.html`
- `REQUIREMENTS_COMPLETED.md` (this file)

### Modified Files:
- `UserRepository.java` - Thêm `findByEmail()`
- `VideoRepository.java` - Thêm 3 methods (findByTitleContaining, findTop10MostFavorited, findVideosWithoutFavorites)
- `ShareRepository.java` - Thêm 2 methods (findSharedVideosIn2024, findSharesByVideoId)
- `FavoriteRepository.java` - Thêm `countByVideoId()`
- `UserService.java` - Thêm `findByEmail()`
- `VideoService.java` - Thêm 3 wrapper methods
- `ShareService.java` - Thêm 2 wrapper methods
- `index.html` - Thêm links cho Share Summary và Test Query pages

## Cách Sử Dụng

### 1. Chạy Application
```bash
cd lab4_final
mvnw spring-boot:run
```

### 2. Truy Cập UI
- Home Page: http://localhost:8080/
- Query Tests: http://localhost:8080/test-query
- Share Summary: http://localhost:8080/share-summary

### 3. Sử Dụng REST API
```bash
# Tìm user theo email
curl http://localhost:8080/api/query/user/email/admin@fpt.edu.vn

# Search video
curl http://localhost:8080/api/query/video/search?keyword=video

# Top 10 favorited
curl http://localhost:8080/api/query/video/top10-favorited

# Shared in 2024
curl http://localhost:8080/api/query/share/2024
```

## Notes
- Email field trong User table có UNIQUE constraint
- Tất cả queries sử dụng Spring Data JPA best practices
- Support cả JPQL và Native SQL queries
- Responsive UI với Bootstrap 5
- Console output để debugging
