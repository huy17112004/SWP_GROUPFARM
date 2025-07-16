# Pagination Loader - Hướng dẫn sử dụng

## Tổng quan
Module phân trang thông minh với các tính năng:
- Hiển thị tối đa 5 trang xung quanh trang hiện tại
- Tự động ẩn khi chỉ có 1 trang
- Loading state và scroll to top
- Responsive và accessible
- Dark mode support

## Cách sử dụng

### 1. Import module
```javascript
import { renderSmartPagination, attachPaginationEvents } from '../assets/jsLogic/pagination-loader.js';
```

### 2. Render phân trang
```javascript
// Cơ bản
renderSmartPagination('.pagination-container', totalPages, currentPage);

// Với tùy chọn số trang hiển thị
renderSmartPagination('.pagination-container', totalPages, currentPage, 7);
```

### 3. Gắn event listener
```javascript
// Cơ bản
attachPaginationEvents('.pagination-container', (page) => {
    // Xử lý khi người dùng click vào trang
    loadData(page);
});

// Với tùy chọn
attachPaginationEvents('.pagination-container', async (page) => {
    await loadData(page);
}, {
    scrollToTop: true,    // Tự động scroll lên đầu trang
    showLoading: true     // Hiển thị loading state
});
```

## Ví dụ hoàn chỉnh

```javascript
import { renderSmartPagination, attachPaginationEvents } from '../assets/jsLogic/pagination-loader.js';

let currentPage = 0;
let totalPages = 10;

// Hàm load dữ liệu
async function loadProducts(page) {
    try {
        const response = await fetch(`/api/products?page=${page}&size=10`);
        const data = await response.json();
        
        // Render sản phẩm
        renderProducts(data.products);
        
        // Render phân trang
        renderSmartPagination('.pagination', data.totalPages, page);
        
        currentPage = page;
        totalPages = data.totalPages;
    } catch (error) {
        console.error('Lỗi khi load sản phẩm:', error);
    }
}

// Gắn event listener
attachPaginationEvents('.pagination', loadProducts, {
    scrollToTop: true,
    showLoading: true
});

// Load trang đầu tiên
loadProducts(0);
```

## HTML Structure

```html
<!-- Container cho phân trang -->
<nav aria-label="Product pagination">
    <ul class="pagination custom-pagination" id="pagination">
        <!-- Pagination sẽ được render ở đây -->
    </ul>
</nav>
```

## CSS Classes

### Tự động thêm:
- `.loading` - Khi đang load dữ liệu
- `.active` - Trang hiện tại
- `.disabled` - Nút Previous/Next khi không thể click

### Tùy chỉnh:
- `.custom-pagination` - Container chính
- `.page-link` - Link của từng trang
- `.page-item` - Item của từng trang

## Tính năng

### 1. Hiển thị thông minh
- Luôn hiển thị trang đầu và trang cuối
- Hiển thị dấu "..." khi có khoảng trống
- Tối đa 5 trang xung quanh trang hiện tại

### 2. Loading State
- Tự động thêm class `.loading` khi đang load
- Disable tất cả nút trong quá trình load
- Tự động xóa loading state khi hoàn thành

### 3. Scroll to Top
- Tự động scroll lên đầu trang khi chuyển trang
- Có thể tắt tính năng này

### 4. Responsive
- Tự động điều chỉnh kích thước trên mobile
- Gap nhỏ hơn trên màn hình nhỏ

### 5. Accessibility
- ARIA labels
- Keyboard navigation
- Focus indicators
- High contrast support

### 6. Dark Mode
- Tự động hỗ trợ dark mode
- Sử dụng `prefers-color-scheme`

## API Reference

### renderSmartPagination(containerSelector, totalPages, currentPage, maxVisiblePages)
- `containerSelector`: CSS selector cho container
- `totalPages`: Tổng số trang
- `currentPage`: Trang hiện tại (bắt đầu từ 0)
- `maxVisiblePages`: Số trang tối đa hiển thị (mặc định: 5)

### attachPaginationEvents(containerSelector, onPageChange, options)
- `containerSelector`: CSS selector cho container
- `onPageChange`: Callback function khi click vào trang
- `options`: Object tùy chọn
  - `scrollToTop`: Boolean, có scroll lên đầu không (mặc định: true)
  - `showLoading`: Boolean, có hiển thị loading không (mặc định: true)

### generatePaginationHTML(totalPages, currentPage, maxVisiblePages)
- Trả về HTML string cho phân trang (không render)
- Hữu ích khi muốn tùy chỉnh cách render

## Demo
Xem file `pagination-demo.html` để thấy các ví dụ khác nhau của phân trang. 