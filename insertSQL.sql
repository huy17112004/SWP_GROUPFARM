USE ASSSWP8
GO

INSERT INTO Category(CategoryName, ShelfLifeDays)
VALUES('Hoa qua xanh', 3),
	  ('Hoa qua vang', 2),
	  ('Hoa qua do', 1)

GO
INSERT INTO Product(ProductName,CategoryID,Description,EntryPrice,RetailPrice,WholesalePrice)
VALUES('Oi ngon', 1,'Oi thanh hoa ngon',5000,7000,10000),
	  ('Chuoi xin',2,'Chuoi thanh hoa xin',10000,15000,12000),
	  ('Roi vip',3,'Roi vip phu tho',10000,12000,11000)

GO
INSERT INTO ProductImage(ProductID,ImageUrl)
VALUES
(1,'/images/oi-1.jpg'),
(1,'/images/oi-2.jpg'),
(2,'/images/chuoi-1.jpg'),
(2,'/images/chuoi-2.jpg'),
(2,'/images/chuoi-3.jpg'),
(2,'/images/chuoi-4.jpg'),
(3,'/images/roi-1.jpg'),
(3,'/images/roi-2.jpg'),
(3,'/images/roi-3.jpg'),
(3,'/images/roi-4.jpg')
