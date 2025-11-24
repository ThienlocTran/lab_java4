-- FIX DATABASE COLUMN NAMES (Userld -> UserId, Videold -> VideoId)
USE PolyOE;
GO

-- =======================================
-- FIX Favorite TABLE
-- =======================================
-- Xóa constraint UNIQUE hiện tại
ALTER TABLE Favorite DROP CONSTRAINT [UQ__Favorite__Userld__Videold];
GO

-- Rename cột Userld -> UserId
EXEC sp_rename 'Favorite.Userld', 'UserId', 'COLUMN';
GO

-- Rename cột Videold -> VideoId
EXEC sp_rename 'Favorite.Videold', 'VideoId', 'COLUMN';
GO

-- Tạo lại constraint UNIQUE với tên cột mới
ALTER TABLE Favorite 
ADD CONSTRAINT UQ_Favorite_UserId_VideoId UNIQUE (UserId, VideoId);
GO

-- =======================================
-- FIX Share TABLE
-- =======================================
-- Rename cột Userld -> UserId
EXEC sp_rename 'Share.Userld', 'UserId', 'COLUMN'; 
GO

-- Rename cột Videold -> VideoId
EXEC sp_rename 'Share.Videold', 'VideoId', 'COLUMN';
GO

-- =======================================
-- VERIFY CHANGES
-- =======================================
PRINT N'Favorite columns:';
SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Favorite' ORDER BY ORDINAL_POSITION;

PRINT N'';
PRINT N'Share columns:';
SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Share' ORDER BY ORDINAL_POSITION;

PRINT N'';
PRINT N'Data verification:';
SELECT COUNT(*) AS favorite_count FROM Favorite;
SELECT COUNT(*) AS share_count FROM Share;
GO
