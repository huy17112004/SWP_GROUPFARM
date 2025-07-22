# Hướng dẫn sử dụng chức năng Edit Sản phẩm

## Tổng quan
Chức năng edit sản phẩm cho phép người dùng chỉnh sửa thông tin sản phẩm một cách dễ dàng thông qua giao diện modal. Chức năng này đã được tích hợp vào trang Seller Dashboard.

## Các tính năng chính

### 1. Hiển thị danh sách sản phẩm
- Danh sách sản phẩm được hiển thị trong bảng với các cột:
  - Hình ảnh sản phẩm
  - Tên sản phẩm
  - Mô tả
  - Loại sản phẩm
  - Giá bán buôn
  - Các nút hành động (Edit/Delete)

### 2. Chức năng Edit
- **Nút Edit**: Click vào icon edit (biểu tượng bút chì) để mở modal chỉnh sửa
- **Modal Edit**: Giao diện form cho phép chỉnh sửa:
  - Tên sản phẩm (bắt buộc)
  - Giá bán buôn (bắt buộc)
  - Mô tả sản phẩm (tối đa 50 ký tự)
  - Loại sản phẩm (bắt buộc)
  - URL ảnh sản phẩm (có thể nhập nhiều URL, phân cách bằng dấu phẩy)

### 3. Validation và Error Handling
- **Validation phía client**: Kiểm tra các trường bắt buộc trước khi gửi request
- **Validation phía server**: Kiểm tra dữ liệu và trả về lỗi nếu có
- **Toast notifications**: Hiển thị thông báo thành công/lỗi
- **Loading state**: Hiển thị trạng thái đang xử lý

### 4. Cập nhật ảnh sản phẩm
- Hỗ trợ cập nhật nhiều ảnh cho một sản phẩm
- Tự động xóa ảnh cũ và thêm ảnh mới
- Xử lý URL ảnh trống hoặc không hợp lệ

## Cấu trúc API

### Endpoint: `PUT /api/products-dashboard/{id}`

**Request Body:**
```json
{
  "productName": "Tên sản phẩm",
  "wholesalePrice": 100000.00,
  "description": "Mô tả sản phẩm",
  "categoryId": 1,
  "imageUrl": ["url1.jpg", "url2.jpg"]
}
```

**Response:**
```json
{
  "id": 1,
  "productName": "Tên sản phẩm",
  "wholesalePrice": 100000.00,
  "description": "Mô tả sản phẩm",
  "imageUrl": ["url1.jpg", "url2.jpg"],
  "categoryId": 1,
  "categoryName": "Tên loại sản phẩm"
}
```

### Endpoint: `GET /api/categories`
Lấy danh sách tất cả loại sản phẩm để hiển thị trong dropdown.

## Cách sử dụng

### 1. Truy cập trang Seller Dashboard
- Mở file `seller-dashboard.html`
- Chuyển đến tab "Tất Cả Sản Phẩm"

### 2. Chỉnh sửa sản phẩm
1. Tìm sản phẩm cần chỉnh sửa trong bảng
2. Click vào icon edit (biểu tượng bút chì) ở cột "Hành động"
3. Modal edit sẽ mở với thông tin hiện tại của sản phẩm
4. Chỉnh sửa các thông tin cần thiết
5. Click "Lưu thay đổi" để lưu hoặc "Hủy" để đóng modal

### 3. Validation
- Tên sản phẩm không được để trống
- Giá bán buôn phải lớn hơn 0
- Loại sản phẩm phải được chọn
- Mô tả tối đa 50 ký tự

## Cấu trúc file

### Backend
- `ProductDashBoardServlet.java`: Xử lý HTTP requests
- `ProductDashboardService.java`: Business logic
- `ProductDashboardDAO.java`: Data access
- `ProductDashboardDTO.java`: Data transfer object

### Frontend
- `seller-dashboard.html`: Giao diện chính
- `productPagination.js`: Logic JavaScript cho edit và pagination

## Tính năng bổ sung

### 1. Toast Notifications
- Hiển thị thông báo thành công/lỗi
- Tự động ẩn sau vài giây
- Hỗ trợ nhiều loại thông báo (success, error, info)

### 2. Responsive Design
- Modal tương thích với mobile
- Giao diện thích ứng với kích thước màn hình

### 3. Loading States
- Hiển thị trạng thái đang xử lý
- Disable button khi đang gửi request

### 4. Error Handling
- Xử lý lỗi network
- Hiển thị thông báo lỗi chi tiết
- Fallback khi không thể load dữ liệu

## Troubleshooting

### Lỗi thường gặp

1. **Không thể load danh sách loại sản phẩm**
   - Kiểm tra kết nối database
   - Kiểm tra endpoint `/api/categories`

2. **Không thể cập nhật sản phẩm**
   - Kiểm tra validation
   - Kiểm tra log server
   - Đảm bảo categoryId tồn tại

3. **Modal không mở**
   - Kiểm tra Bootstrap JS
   - Kiểm tra console browser

### Debug
- Mở Developer Tools (F12)
- Kiểm tra tab Console và Network
- Xem log server để debug backend

## Cải tiến tương lai

1. **Upload ảnh**: Thêm chức năng upload ảnh thay vì chỉ nhập URL
2. **Preview ảnh**: Hiển thị preview ảnh trong modal
3. **Bulk edit**: Chỉnh sửa nhiều sản phẩm cùng lúc
4. **Version history**: Lưu lịch sử thay đổi
5. **Audit trail**: Ghi log người thực hiện thay đổi 