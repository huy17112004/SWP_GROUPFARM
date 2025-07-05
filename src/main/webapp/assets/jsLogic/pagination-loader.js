/**
 * Hàm render phân trang thông minh
 * @param {string} containerSelector - CSS selector cho container phân trang
 * @param {number} totalPages - Tổng số trang
 * @param {number} currentPage - Trang hiện tại (bắt đầu từ 0)
 * @param {number} maxVisiblePages - Số trang tối đa hiển thị (mặc định: 5)
 */
export function renderSmartPagination(containerSelector, totalPages, currentPage, maxVisiblePages = 5) {
    const paginationContainer = document.querySelector(containerSelector);
    if (!paginationContainer) {
        console.warn(`Không tìm thấy container phân trang: ${containerSelector}`);
        return;
    }
    
    // Nếu chỉ có 1 trang hoặc không có trang nào, ẩn phân trang
    if (totalPages <= 1) {
        paginationContainer.style.display = 'none';
        return;
    }
    
    paginationContainer.style.display = 'block';
    
    // Thêm class loading nếu cần
    paginationContainer.classList.remove('loading');
    let html = '';
    
    // Previous button
    html += `<li class="page-item${currentPage === 0 ? ' disabled' : ''}">`
        + `<a class="page-link" href="javascript:void(0)" data-page="${currentPage - 1}">`
        + `<i class="fa-solid fa-chevron-left"></i></a></li>`;
    
    // Logic hiển thị trang thông minh
    let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(totalPages - 1, startPage + maxVisiblePages - 1);
    
    // Điều chỉnh startPage nếu endPage quá gần cuối
    if (endPage - startPage < maxVisiblePages - 1) {
        startPage = Math.max(0, endPage - maxVisiblePages + 1);
    }
    
    // Hiển thị trang đầu nếu cần
    if (startPage > 0) {
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" data-page="0">1</a></li>`;
        if (startPage > 1) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }
    }
    
    // Hiển thị các trang trong khoảng
    for (let i = startPage; i <= endPage; i++) {
        html += `<li class="page-item${i === currentPage ? ' active' : ''}">`
            + `<a class="page-link" href="javascript:void(0)" data-page="${i}">${i + 1}</a></li>`;
    }
    
    // Hiển thị trang cuối nếu cần
    if (endPage < totalPages - 1) {
        if (endPage < totalPages - 2) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" data-page="${totalPages - 1}">${totalPages}</a></li>`;
    }
    
    // Next button
    html += `<li class="page-item${currentPage === totalPages - 1 ? ' disabled' : ''}">`
        + `<a class="page-link" href="javascript:void(0)" data-page="${currentPage + 1}">`
        + `<i class="fa-solid fa-chevron-right"></i></a></li>`;
    
    paginationContainer.innerHTML = html;
}

/**
 * Hàm gắn event listener cho phân trang
 * @param {string} containerSelector - CSS selector cho container phân trang
 * @param {Function} onPageChange - Callback function khi người dùng click vào trang
 */
export function attachPaginationEvents(containerSelector, onPageChange, options = {}) {
    const { scrollToTop = true, showLoading = true } = options;
    
    document.addEventListener('click', async (e) => {
        const pageLink = e.target.closest(`${containerSelector} a.page-link`);
        if (pageLink) {
            e.preventDefault();
            const page = parseInt(pageLink.getAttribute('data-page'));
            if (!isNaN(page) && page >= 0 && onPageChange) {
                // Thêm loading state
                if (showLoading) {
                    const paginationContainer = document.querySelector(containerSelector);
                    if (paginationContainer) {
                        paginationContainer.classList.add('loading');
                    }
                }
                
                // Scroll to top nếu cần
                if (scrollToTop) {
                    window.scrollTo({ top: 0, behavior: 'smooth' });
                }
                
                try {
                    await onPageChange(page);
                } finally {
                    // Xóa loading state
                    if (showLoading) {
                        const paginationContainer = document.querySelector(containerSelector);
                        if (paginationContainer) {
                            paginationContainer.classList.remove('loading');
                        }
                    }
                }
            }
        }
    });
}

/**
 * Hàm tạo HTML cho phân trang (không render, chỉ trả về HTML string)
 * @param {number} totalPages - Tổng số trang
 * @param {number} currentPage - Trang hiện tại
 * @param {number} maxVisiblePages - Số trang tối đa hiển thị
 * @returns {string} HTML string cho phân trang
 */
export function generatePaginationHTML(totalPages, currentPage, maxVisiblePages = 5) {
    if (totalPages <= 1) return '';
    
    let html = '';
    
    // Previous button
    html += `<li class="page-item${currentPage === 0 ? ' disabled' : ''}">`
        + `<a class="page-link" href="javascript:void(0)" data-page="${currentPage - 1}">`
        + `<i class="fa-solid fa-chevron-left"></i></a></li>`;
    
    // Logic hiển thị trang thông minh
    let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(totalPages - 1, startPage + maxVisiblePages - 1);
    
    if (endPage - startPage < maxVisiblePages - 1) {
        startPage = Math.max(0, endPage - maxVisiblePages + 1);
    }
    
    // Hiển thị trang đầu nếu cần
    if (startPage > 0) {
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" data-page="0">1</a></li>`;
        if (startPage > 1) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }
    }
    
    // Hiển thị các trang trong khoảng
    for (let i = startPage; i <= endPage; i++) {
        html += `<li class="page-item${i === currentPage ? ' active' : ''}">`
            + `<a class="page-link" href="javascript:void(0)" data-page="${i}">${i + 1}</a></li>`;
    }
    
    // Hiển thị trang cuối nếu cần
    if (endPage < totalPages - 1) {
        if (endPage < totalPages - 2) {
            html += `<li class="page-item disabled"><span class="page-link">...</span></li>`;
        }
        html += `<li class="page-item"><a class="page-link" href="javascript:void(0)" data-page="${totalPages - 1}">${totalPages}</a></li>`;
    }
    
    // Next button
    html += `<li class="page-item${currentPage === totalPages - 1 ? ' disabled' : ''}">`
        + `<a class="page-link" href="javascript:void(0)" data-page="${currentPage + 1}">`
        + `<i class="fa-solid fa-chevron-right"></i></a></li>`;
    
    return html;
} 