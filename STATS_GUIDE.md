# Hướng dẫn Sử dụng Hệ thống Thống kê & Báo cáo

## Tổng quan

Hệ thống thống kê và báo cáo của GroupFarm được chia thành **5 màn hình chính** để quản lý và theo dõi hiệu suất kinh doanh một cách hiệu quả.

## 📊 Các Màn hình Chức năng

### 1. **Dashboard Tổng quan** (`dashboard.html`)
**Mục đích:** Xem tổng quan tất cả thống kê quan trọng

**Chức năng chính:**
- Số đơn hàng hôm nay
- Doanh thu hôm nay  
- Tỷ lệ giao hàng thành công
- Top 5 sản phẩm bán chạy hôm nay
- Thống kê nhanh (đơn hàng thành công/thất bại)
- Liên kết nhanh đến các màn hình khác

**API sử dụng:**
- `GET /api/stats` - Số đơn hàng hôm nay
- `GET /api/revenue?period=today` - Doanh thu hôm nay
- `GET /api/shipping-stats` - Thống kê giao hàng
- `GET /api/top-products?period=today&limit=5` - Top sản phẩm

---

### 2. **Thống kê Doanh thu** (`revenue-stats.html`)
**Mục đích:** Phân tích doanh thu theo thời gian

**Chức năng chính:**
- Doanh thu theo ngày/tuần/tháng/năm
- Biểu đồ doanh thu theo thời gian
- So sánh doanh thu các kỳ
- Chi tiết doanh thu từng thời gian
- Phân tích tăng trưởng

**API sử dụng:**
- `GET /api/revenue` - Tất cả doanh thu
- `GET /api/revenue?period=today` - Doanh thu hôm nay
- `GET /api/revenue?period=week` - Doanh thu tuần này
- `GET /api/revenue?period=month` - Doanh thu tháng này
- `GET /api/revenue?period=year` - Doanh thu năm nay

---

### 3. **Thống kê Đơn hàng** (`order-stats.html`)
**Mục đích:** Theo dõi trạng thái và hiệu suất đơn hàng

**Chức năng chính:**
- Số đơn hàng theo thời gian
- Trạng thái đơn hàng (thành công/đang xử lý/thất bại)
- Tỷ lệ thành công/thất bại
- Biểu đồ trạng thái đơn hàng
- Timeline đơn hàng gần đây

**API sử dụng:**
- `GET /api/stats` - Số đơn hàng hôm nay
- `GET /api/shipping-stats` - Thống kê giao hàng

---

### 4. **Top Sản phẩm Bán chạy** (`top-products.html`)
**Mục đích:** Xem sản phẩm bán chạy nhất

**Chức năng chính:**
- Top sản phẩm theo ngày/tuần/tháng
- Số lượng bán và doanh thu từng sản phẩm
- Biểu đồ so sánh sản phẩm
- Thống kê tổng doanh thu top sản phẩm
- Tùy chọn số lượng hiển thị (5, 10, 20, 50)

**API sử dụng:**
- `GET /api/top-products?period=today&limit=10` - Top sản phẩm hôm nay
- `GET /api/top-products?period=week&limit=10` - Top sản phẩm tuần này
- `GET /api/top-products?period=month&limit=10` - Top sản phẩm tháng này

---

### 5. **Báo cáo Giao hàng** (`shipping-report.html`)
**Mục đích:** Phân tích hiệu suất giao hàng

**Chức năng chính:**
- Tỷ lệ giao hàng thành công
- Thời gian giao hàng trung bình
- Hiệu suất giao hàng theo khu vực
- Giao hàng gần đây
- Vấn đề giao hàng và cải thiện

**API sử dụng:**
- `GET /api/shipping-stats` - Thống kê giao hàng

---

### 6. **Trang Chính Thống kê** (`stats-dashboard.html`)
**Mục đích:** Trang index điều hướng đến các màn hình thống kê

**Chức năng chính:**
- Thống kê nhanh (đơn hàng, doanh thu, tỷ lệ thành công)
- Liên kết đến tất cả màn hình thống kê
- Hoạt động gần đây
- Xuất báo cáo và cài đặt

---

## 🔧 Cách Sử dụng

### Truy cập Hệ thống
1. Đăng nhập vào hệ thống admin
2. Truy cập `stats-dashboard.html` để xem tổng quan
3. Chọn màn hình thống kê cần xem

### Lọc Dữ liệu
- **Thời gian:** Hôm nay, Tuần này, Tháng này, Năm nay
- **Số lượng:** Top 5, 10, 20, 50 sản phẩm
- **Sắp xếp:** Theo số lượng bán hoặc doanh thu

### Tự động Cập nhật
- Dashboard: Cập nhật mỗi 5 phút
- Các màn hình khác: Cập nhật khi thay đổi filter

---

## 📈 Các Chỉ số Quan trọng

### Doanh thu
- **Doanh thu hôm nay:** Tổng doanh thu từ đơn hàng hoàn thành hôm nay
- **Doanh thu tuần/tháng/năm:** Tổng doanh thu theo kỳ
- **Tăng trưởng:** So sánh với kỳ trước

### Đơn hàng
- **Tổng đơn hàng:** Số lượng đơn hàng được tạo
- **Thành công:** Đơn hàng giao thành công
- **Thất bại:** Đơn hàng giao thất bại hoặc hủy
- **Tỷ lệ thành công:** (Thành công / Tổng) × 100%

### Sản phẩm
- **Top sản phẩm:** Sản phẩm bán nhiều nhất
- **Số lượng bán:** Tổng số lượng đã bán
- **Doanh thu sản phẩm:** Doanh thu từ sản phẩm đó

### Giao hàng
- **Thời gian trung bình:** Thời gian giao hàng trung bình
- **Hiệu suất khu vực:** Tỷ lệ thành công theo địa điểm
- **Vấn đề giao hàng:** Các vấn đề thường gặp

---

## 🚀 Tính năng Nâng cao

### Biểu đồ Tương tác
- Hover để xem chi tiết
- Click để phóng to
- Responsive trên mobile

### Xuất Báo cáo
- PDF báo cáo chi tiết
- Excel dữ liệu thô
- Email báo cáo định kỳ

### Cài đặt Thống kê
- Tùy chỉnh thời gian cập nhật
- Chọn chỉ số hiển thị
- Thiết lập cảnh báo

---

## 🔍 Troubleshooting

### Lỗi thường gặp
1. **Không load được dữ liệu:** Kiểm tra kết nối API
2. **Biểu đồ không hiển thị:** Kiểm tra JavaScript console
3. **Dữ liệu không cập nhật:** Refresh trang hoặc kiểm tra session

### Hỗ trợ
- Liên hệ admin để được hỗ trợ kỹ thuật
- Xem log hệ thống để debug
- Kiểm tra quyền truy cập API

---

## 📱 Responsive Design

Tất cả màn hình thống kê đều được thiết kế responsive:
- **Desktop:** Hiển thị đầy đủ tính năng
- **Tablet:** Tối ưu layout
- **Mobile:** Giao diện đơn giản, dễ sử dụng

---

## 🔄 Cập nhật Hệ thống

Hệ thống sẽ được cập nhật thường xuyên với các tính năng mới:
- Thêm biểu đồ phân tích nâng cao
- Tích hợp AI để dự đoán xu hướng
- Báo cáo tự động qua email
- Dashboard tùy chỉnh cho từng user 