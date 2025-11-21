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

-- CHÈN DỮ LIỆU MẪU Users
INSERT INTO Users (password, fullname, email, admin) VALUES
('123456', N'Nguyễn Văn Tèo', 'teo@gmail.com', 0),
('123456', N'Nguyễn Văn A', 'a@gmail.com', 0),
('123456', N'Nguyễn Văn B', 'b@gmail.com', 1);
GO

-- CHÈN DỮ LIỆU MẪU Videos
INSERT INTO Videos (title, poster, views, description, active) VALUES
(N'Kên võ tập 3', N'poster1.jpg', 150, N'Video hướng dẫn kên võ cơ bản', 1),
(N'Lập trình Java', N'poster2.jpg', 250, N'Hướng dẫn lập trình Java từ cơ bản đến nâng cao', 1),
(N'Spring Boot Tutorial', N'poster3.jpg', 180, N'Học Spring Boot từ đầu', 1),
(N'Database Design', N'poster4.jpg', 120, N'Thiết kế cơ sở dữ liệu hiệu quả', 1),
(N'Web Development', N'poster5.jpg', 200, N'Phát triển web với HTML, CSS, JavaScript', 1);
GO

-- CHÈN DỮ LIỆU MẪU Favorite
INSERT INTO Favorite (Userld, Videold, like_date) VALUES
(1, 1, '2024-12-01'), (1, 2, '2024-12-02'), (1, 3, '2024-12-03'),
(2, 4, '2024-12-02'), (3, 5, '2024-12-03');
GO

-- CHÈN DỮ LIỆU MẪU Share
INSERT INTO Share (Userld, Videold, emails, shareDate) VALUES
(1, 1, 'user1@gmail.com;user2@gmail.com', '2024-12-01'),
(2, 3, 'user3@gmail.com', '2024-12-02'),
(3, 2, 'user4@gmail.com;user5@gmail.com', '2024-12-03');
GO

-- KIỂM TRA DỮ LIỆU
SELECT * FROM Users;
SELECT * FROM Videos;
SELECT * FROM Favorite;
SELECT * FROM Share;
GO