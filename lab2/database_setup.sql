-- Tạo Database PolyOE (nếu chưa tồn tại)
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'PolyOE')
BEGIN
    CREATE DATABASE PolyOE;
END
GO

-- Sử dụng database PolyOE
USE PolyOE;
GO

-- Tạo table Users
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Users')
BEGIN
    CREATE TABLE Users (
        id INT PRIMARY KEY IDENTITY(1,1),
        password NVARCHAR(50) NOT NULL,
        fullname NVARCHAR(50) NOT NULL,
        email NVARCHAR(50) NOT NULL,
        admin BIT DEFAULT 0
    );
END
GO

-- Chèn dữ liệu mẫu
INSERT INTO Users (password, fullname, email, admin) VALUES
('123456', 'Nguyễn Văn A', 'a@gmail.com', 0),
('123456', 'Nguyễn Văn B', 'b@gmail.com', 0),
('123456', 'Nguyễn Văn C', 'c@gmail.com', 1);
GO

-- Kiểm tra dữ liệu
SELECT * FROM Users;
GO
