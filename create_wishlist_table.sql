-- Tạo bảng Wishlist
USE ASSSWP8
GO

-- Tạo bảng Wishlist nếu chưa tồn tại
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Wishlist' AND xtype='U')
BEGIN
    CREATE TABLE Wishlist (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        customer_id INT NOT NULL,
        product_id INT NOT NULL,
        created_at DATETIME2 DEFAULT GETDATE(),
        
        -- Foreign key constraints
        CONSTRAINT FK_Wishlist_Customer FOREIGN KEY (customer_id) REFERENCES WholesaleCustomer(AccountID),
        CONSTRAINT FK_Wishlist_Product FOREIGN KEY (product_id) REFERENCES Product(ProductID),
        
        -- Unique constraint để tránh duplicate
        CONSTRAINT UQ_Wishlist_Customer_Product UNIQUE (customer_id, product_id)
    );
    
    PRINT 'Bảng Wishlist đã được tạo thành công!';
END
ELSE
BEGIN
    PRINT 'Bảng Wishlist đã tồn tại!';
END
GO

-- Tạo index để tối ưu performance
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Wishlist_CustomerId')
BEGIN
    CREATE INDEX IX_Wishlist_CustomerId ON Wishlist(customer_id);
    PRINT 'Index IX_Wishlist_CustomerId đã được tạo!';
END

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Wishlist_ProductId')
BEGIN
    CREATE INDEX IX_Wishlist_ProductId ON Wishlist(product_id);
    PRINT 'Index IX_Wishlist_ProductId đã được tạo!';
END
GO

-- Thêm dữ liệu mẫu cho Wishlist (tùy chọn)
-- INSERT INTO Wishlist (customer_id, product_id) VALUES (1, 1), (1, 2), (2, 1);
-- GO 