-- SỬ DỤNG DATABASE
USE PolyOE;
GO

-- XÓA CÁC BẢNG (DROP TABLES) THEO ĐÚNG THỨ TỰ (FK trước, PK sau)
-- Loại bỏ các lệnh DROP bị lặp và thừa
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Share')
    DROP TABLE Share;
GO

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Favorite')
    DROP TABLE Favorite;
GO

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Videos')
    DROP TABLE Videos;
GO

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Users')
    DROP TABLE Users;
GO

-- KHÔNG CẦN CÁC LỆNH DROP TABLE User/Videos/Favorite/Share LẶP LẠI
-- VÌ CÁC LỆNH IF EXISTS ĐÃ XỬ LÝ

-- TẠO TABLE Users
CREATE TABLE Users (
    id INT PRIMARY KEY IDENTITY(1,1),
    password NVARCHAR(50) NOT NULL,
    fullname NVARCHAR(50) NOT NULL,
    email NVARCHAR(50) NOT NULL UNIQUE, -- Thêm ràng buộc UNIQUE
    admin BIT DEFAULT 0
);
GO

-- TẠO TABLE Videos
CREATE TABLE Videos (
    id INT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(100) NOT NULL,
    poster NVARCHAR(255),
    views INT DEFAULT 0,
    description NVARCHAR(MAX),
    active BIT DEFAULT 1
);
GO

-- TẠO TABLE Favorite (FKs trỏ đến Users và Videos, CẶP UNIQUE)
CREATE TABLE Favorite (
    id INT PRIMARY KEY IDENTITY(1,1),
    Userld INT NOT NULL,
    Videold INT NOT NULL,
    like_date DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (Userld) REFERENCES Users(id),
    FOREIGN KEY (Videold) REFERENCES Videos(id),
    UNIQUE (Userld, Videold) -- Ràng buộc duy nhất cho cặp User-Video
);
GO

-- TẠO TABLE Share (FKs trỏ đến Users và Videos)
CREATE TABLE Share (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    Userld INT NOT NULL,
    Videold INT NOT NULL,
    emails NVARCHAR(MAX) NOT NULL,
    shareDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (Userld) REFERENCES Users(id),
    FOREIGN KEY (Videold) REFERENCES Videos(id)
);
GO

-- ============================================
-- CHÈN DỮ LIỆU MẪU Users (10 users)
-- ============================================
INSERT INTO Users (password, fullname, email, admin) VALUES
('123456', N'Nguyễn Văn Tèo', 'teo@gmail.com', 0),
('123456', N'Nguyễn Văn A', 'a@fpt.edu.vn', 0),
('123456', N'Phạm Thị B', 'b@fpt.edu.vn', 0),
('123456', N'Trần Minh C', 'c@fpt.edu.vn', 0),
('123456', N'Lê Hoàng D', 'd@gmail.com', 0),
('123456', N'Vũ Thái E', 'e@fpt.edu.vn', 1),
('123456', N'Hoàng Kim F', 'f@gmail.com', 0),
('123456', N'Đặng Việt G', 'g@fpt.edu.vn', 0),
('123456', N'Bùi Công H', 'h@gmail.com', 0),
('123456', N'Ngô Minh I', 'i@fpt.edu.vn', 0);
GO

-- ============================================
-- CHÈN DỮ LIỆU MẪU Videos (25 videos)
-- ============================================
INSERT INTO Videos (title, poster, views, description, active) VALUES
(N'Lập Trình Java Cơ Bản', N'poster1.jpg', 250, N'Hướng dẫn lập trình Java từ cơ bản đến nâng cao', 1),
(N'Spring Boot Tutorial', N'poster2.jpg', 180, N'Học Spring Boot - Web Framework phổ biến nhất', 1),
(N'Database Design Concepts', N'poster3.jpg', 120, N'Thiết kế cơ sở dữ liệu hiệu quả', 1),
(N'HTML CSS Web Design', N'poster4.jpg', 200, N'Phát triển web frontend với HTML, CSS, JavaScript', 1),
(N'Python for Data Science', N'poster5.jpg', 300, N'Sử dụng Python cho phân tích dữ liệu', 1),
(N'Git & GitHub Workflow', N'poster6.jpg', 95, N'Quản lý mã nguồn với Git', 1),
(N'React.js Advanced', N'poster7.jpg', 220, N'React advanced patterns và hooks', 1),
(N'Docker Container Basics', N'poster8.jpg', 150, N'Containerization với Docker', 1),
(N'SQL Optimization Tips', N'poster9.jpg', 130, N'Tối ưu hóa các truy vấn SQL', 1),
(N'Microservices Architecture', N'poster10.jpg', 170, N'Xây dựng hệ thống microservices', 1),
(N'Machine Learning 101', N'poster11.jpg', 280, N'Giới thiệu Machine Learning', 1),
(N'API Development REST', N'poster12.jpg', 210, N'Phát triển RESTful APIs', 1),
(N'Cloud Computing AWS', N'poster13.jpg', 190, N'Điện toán đám mây với AWS', 1),
(N'Secure Coding Practices', N'poster14.jpg', 110, N'Lập trình bảo mật', 1),
(N'Testing & TDD', N'poster15.jpg', 140, N'Unit testing và Test-Driven Development', 1),
(N'Angular Framework', N'poster16.jpg', 160, N'Phát triển web với Angular', 1),
(N'Vue.js Essentials', N'poster17.jpg', 135, N'Framework Vue.js cho web', 1),
(N'Node.js Backend', N'poster18.jpg', 175, N'Backend development với Node.js', 1),
(N'Mobile Development iOS', N'poster19.jpg', 125, N'Phát triển ứng dụng iOS', 1),
(N'Android Development', N'poster20.jpg', 145, N'Lập trình Android', 1),
(N'DevOps Essentials', N'poster21.jpg', 100, N'DevOps practices và tools', 1),
(N'AI & Deep Learning', N'poster22.jpg', 290, N'Deep Learning với Neural Networks', 1),
(N'Blockchain Technology', N'poster23.jpg', 105, N'Công nghệ Blockchain', 1),
(N'Cybersecurity Fundamentals', N'poster24.jpg', 115, N'Nền tảng an ninh mạng', 1),
(N'Advanced JavaScript', N'poster25.jpg', 225, N'JavaScript nâng cao - async, promises, etc', 1);
GO

-- ============================================
-- CHÈN DỮ LIỆU MẪU Favorite (40 records)
-- ============================================
INSERT INTO Favorite (Userld, Videold, like_date) VALUES
-- User 1 (Tèo) thích 8 videos
(1, 1, '2024-12-01'), (1, 2, '2024-12-02'), (1, 5, '2024-12-03'),
(1, 7, '2024-12-04'), (1, 11, '2024-12-05'), (1, 12, '2024-12-06'),
(1, 18, '2024-12-07'), (1, 25, '2024-12-08'),

-- User 2 (A) thích 7 videos
(2, 1, '2024-12-01'), (2, 3, '2024-12-02'), (2, 6, '2024-12-03'),
(2, 9, '2024-12-04'), (2, 13, '2024-12-05'), (2, 16, '2024-12-06'),
(2, 22, '2024-12-07'),

-- User 3 (B) thích 6 videos
(3, 2, '2024-12-01'), (3, 4, '2024-12-02'), (3, 7, '2024-12-03'),
(3, 11, '2024-12-04'), (3, 17, '2024-12-05'), (3, 25, '2024-12-06'),

-- User 4 (C) thích 5 videos
(4, 5, '2024-12-01'), (4, 8, '2024-12-02'), (4, 12, '2024-12-03'),
(4, 15, '2024-12-04'), (4, 20, '2024-12-05'),

-- User 5 (D) thích 8 videos
(5, 1, '2024-12-01'), (5, 2, '2024-12-02'), (5, 5, '2024-12-03'),
(5, 11, '2024-12-04'), (5, 12, '2024-12-05'), (5, 22, '2024-12-06'),
(5, 25, '2024-12-07'), (5, 3, '2024-12-08'),

-- User 6 (E) - admin, thích 4 videos
(6, 7, '2024-12-01'), (6, 13, '2024-12-02'), (6, 18, '2024-12-03'),
(6, 2, '2024-12-04'),

-- User 7 (F) thích 3 videos
(7, 4, '2024-12-01'), (7, 9, '2024-12-02'), (7, 16, '2024-12-03'),

-- User 8 (G) thích 5 videos
(8, 1, '2024-12-01'), (8, 6, '2024-12-02'), (8, 14, '2024-12-03'),
(8, 21, '2024-12-04'), (8, 24, '2024-12-05'),

-- User 9 (H) thích 2 videos (ít thích)
(9, 10, '2024-12-01'), (9, 19, '2024-12-02'),

-- User 10 (I) thích 6 videos
(10, 3, '2024-12-01'), (10, 8, '2024-12-02'), (10, 12, '2024-12-03'),
(10, 17, '2024-12-04'), (10, 20, '2024-12-05'), (10, 23, '2024-12-06');
GO

-- ============================================
-- CHÈN DỮ LIỆU MẦU Share (20 records)
-- ============================================
INSERT INTO Share (Userld, Videold, emails, shareDate) VALUES
-- Chia sẻ trong 2024
(1, 1, N'user1@gmail.com;user2@gmail.com;user3@gmail.com', '2024-01-15'),
(2, 2, N'user4@gmail.com;user5@gmail.com', '2024-02-20'),
(3, 5, N'user6@gmail.com', '2024-03-10'),
(4, 7, N'user7@gmail.com;user8@gmail.com;user9@gmail.com;user10@gmail.com', '2024-04-05'),
(5, 11, N'user11@gmail.com', '2024-05-12'),
(6, 12, N'user12@gmail.com;user13@gmail.com', '2024-06-18'),
(7, 3, N'user14@gmail.com;user15@gmail.com;user16@gmail.com', '2024-07-22'),
(8, 9, N'user17@gmail.com', '2024-08-30'),
(9, 15, N'user18@gmail.com;user19@gmail.com', '2024-09-14'),
(10, 18, N'user20@gmail.com;user21@gmail.com;user22@gmail.com', '2024-10-25'),
(1, 22, N'user23@gmail.com', '2024-11-08'),
(2, 25, N'user24@gmail.com;user25@gmail.com', '2024-12-03'),

-- Chia sẻ trong 2023
(3, 4, N'user26@gmail.com;user27@gmail.com', '2023-06-15'),
(4, 8, N'user28@gmail.com', '2023-07-20'),
(5, 13, N'user29@gmail.com;user30@gmail.com', '2023-08-10'),
(6, 16, N'user31@gmail.com', '2023-09-25'),

-- Chia sẻ trong 2022
(7, 20, N'user32@gmail.com;user33@gmail.com', '2022-10-05'),
(8, 6, N'user34@gmail.com', '2022-11-15'),
(9, 19, N'user35@gmail.com;user36@gmail.com;user37@gmail.com', '2022-12-20'),
(10, 21, N'user38@gmail.com', '2022-05-10');
GO

-- ============================================
-- KIỂM TRA DỮ LIỆU
-- ============================================
PRINT N'';
PRINT N'========================================';
PRINT N'DỮ LIỆU ĐÃ INSERT THÀNH CÔNG';
PRINT N'========================================';

PRINT N'';
PRINT N'USERS:';
SELECT COUNT(*) AS 'Total Users' FROM Users;

PRINT N'';
PRINT N'VIDEOS:';
SELECT COUNT(*) AS 'Total Videos' FROM Videos;

PRINT N'';
PRINT N'FAVORITES:';
SELECT COUNT(*) AS 'Total Favorites' FROM Favorite;

PRINT N'';
PRINT N'SHARES:';
SELECT COUNT(*) AS 'Total Shares' FROM Share;

PRINT N'';
PRINT N'========================================';
PRINT N'STATISTIKA:';
PRINT N'========================================';

PRINT N'';
PRINT N'Top 5 Videos được yêu thích nhất:';
SELECT TOP 5 v.id, v.title, COUNT(f.id) AS like_count
FROM Videos v
LEFT JOIN Favorite f ON v.id = f.Videold
GROUP BY v.id, v.title
ORDER BY like_count DESC;

PRINT N'';
PRINT N'Videos không được thích:';
SELECT COUNT(*) AS no_favorite_count FROM Videos v
WHERE v.id NOT IN (SELECT DISTINCT Videold FROM Favorite);

PRINT N'';
PRINT N'Share trong 2024:';
SELECT COUNT(*) AS share_2024_count FROM Share
WHERE YEAR(shareDate) = 2024;

PRINT N'';
PRINT N'Share trong 2023:';
SELECT COUNT(*) AS share_2023_count FROM Share
WHERE YEAR(shareDate) = 2023;

PRINT N'';
PRINT N'========================================';
PRINT N'Dữ liệu chi tiết:';
PRINT N'========================================';
PRINT N'';
PRINT N'USERS:';
SELECT id, fullname, email, CASE WHEN admin = 1 THEN 'Yes' ELSE 'No' END AS admin FROM Users ORDER BY id;

PRINT N'';
PRINT N'TOP 10 VIDEOS:';
SELECT TOP 10 id, title, views FROM Videos ORDER BY id;

PRINT N'';
PRINT N'FAVORITE COUNT:';
SELECT v.id, v.title, COUNT(f.id) AS favorite_count
FROM Videos v
LEFT JOIN Favorite f ON v.id = f.Videold
GROUP BY v.id, v.title
ORDER BY v.id;

PRINT N'';
PRINT N'========================================';
GO