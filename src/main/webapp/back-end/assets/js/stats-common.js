/**
 * Common JavaScript functions for Statistics Dashboard
 * GroupFarm Admin System
 */

// Global variables
let currentPeriod = 'today';
let refreshInterval = null;

/**
 * Initialize statistics dashboard
 */
function initStatsDashboard() {
    console.log('Initializing Statistics Dashboard...');
    
    // Load initial data
    loadQuickStats();
    
    // Set up auto refresh
    setupAutoRefresh();
    
    // Initialize tooltips
    initTooltips();
    
    // Set up event listeners
    setupEventListeners();
}

/**
 * Load quick statistics for dashboard
 */
function loadQuickStats() {
    console.log('Loading quick statistics...');
    
    // Show loading state
    showLoading();
    
    // Load today's orders
    loadOrdersToday();
    
    // Load today's revenue
    loadRevenueToday();
    
    // Load shipping stats
    loadShippingStats();
    
    // Load top products count
    loadTopProductsCount();
}

/**
 * Load today's orders count
 */
function loadOrdersToday() {
    $.ajax({
        url: '/api/stats',
        method: 'GET',
        success: function(data) {
            updateElement('#todayOrders', data.totalOrders);
            console.log('Orders loaded:', data.totalOrders);
        },
        error: function(xhr) {
            console.error('Error loading orders:', xhr);
            showError('Không thể tải số đơn hàng');
        }
    });
}

/**
 * Load today's revenue
 */
function loadRevenueToday() {
    $.ajax({
        url: '/api/revenue?period=today',
        method: 'GET',
        success: function(data) {
            updateElement('#todayRevenue', formatCurrency(data.todayRevenue));
            console.log('Revenue loaded:', data.todayRevenue);
        },
        error: function(xhr) {
            console.error('Error loading revenue:', xhr);
            showError('Không thể tải doanh thu');
        }
    });
}

/**
 * Load shipping statistics
 */
function loadShippingStats() {
    $.ajax({
        url: '/api/shipping-stats',
        method: 'GET',
        success: function(data) {
            updateElement('#successOrders', data.successOrders);
            updateElement('#failedOrders', data.failedOrders);
            updateElement('#successRate', data.successRate.toFixed(1) + '%');
            
            // Update progress bar if exists
            const progressBar = $('#successProgress');
            if (progressBar.length) {
                progressBar.css('width', data.successRate + '%');
            }
            
            console.log('Shipping stats loaded:', data);
        },
        error: function(xhr) {
            console.error('Error loading shipping stats:', xhr);
            showError('Không thể tải thống kê giao hàng');
        }
    });
}

/**
 * Load top products count
 */
function loadTopProductsCount() {
    $.ajax({
        url: '/api/top-products?period=today&limit=5',
        method: 'GET',
        success: function(data) {
            updateElement('#topProducts', data.length);
            console.log('Top products count loaded:', data.length);
        },
        error: function(xhr) {
            console.error('Error loading top products count:', xhr);
            showError('Không thể tải số sản phẩm bán chạy');
        }
    });
}

/**
 * Load revenue statistics by period
 */
function loadRevenueStats(period = 'all') {
    console.log('Loading revenue stats for period:', period);
    
    const url = period === 'all' ? '/api/revenue' : `/api/revenue?period=${period}`;
    
    $.ajax({
        url: url,
        method: 'GET',
        success: function(data) {
            updateRevenueCards(data);
            updateRevenueDetails(data);
            updateRevenueChart(data);
            console.log('Revenue stats loaded:', data);
        },
        error: function(xhr) {
            console.error('Error loading revenue stats:', xhr);
            showError('Không thể tải thống kê doanh thu');
        }
    });
}

/**
 * Load top products by period
 */
function loadTopProducts(period = 'month', limit = 10) {
    console.log('Loading top products for period:', period, 'limit:', limit);
    
    $.ajax({
        url: `/api/top-products?period=${period}&limit=${limit}`,
        method: 'GET',
        success: function(data) {
            displayTopProductsCards(data);
            displayTopProductsTable(data);
            updateTopProductsChart(data);
            updateTopProductsStats(data);
            console.log('Top products loaded:', data.length);
        },
        error: function(xhr) {
            console.error('Error loading top products:', xhr);
            showError('Không thể tải top sản phẩm');
        }
    });
}

/**
 * Load order statistics
 */
function loadOrderStats(period = 'today') {
    console.log('Loading order stats for period:', period);
    
    // Load basic order stats
    loadOrdersToday();
    
    // Load shipping stats
    loadShippingStats();
    
    // Update charts if they exist
    updateOrderCharts();
    
    // Update order details table
    updateOrderDetailsTable();
}

/**
 * Load shipping report
 */
function loadShippingReport(period = 'today') {
    console.log('Loading shipping report for period:', period);
    
    // Load shipping stats
    loadShippingStats();
    
    // Update charts if they exist
    updateShippingCharts();
    
    // Update performance table
    updateDeliveryPerformanceTable();
    
    // Update recent deliveries
    updateRecentDeliveriesTable();
}

/**
 * Update revenue cards
 */
function updateRevenueCards(data) {
    updateElement('#todayRevenue', formatCurrency(data.todayRevenue));
    updateElement('#weekRevenue', formatCurrency(data.weekRevenue));
    updateElement('#monthRevenue', formatCurrency(data.monthRevenue));
    updateElement('#yearRevenue', formatCurrency(data.yearRevenue));
}

/**
 * Update revenue details table
 */
function updateRevenueDetails(data) {
    const tableBody = $('#revenueDetailsTable');
    if (!tableBody.length) return;
    
    tableBody.empty();
    
    const periods = [
        { name: 'Hôm nay', revenue: data.todayRevenue, orders: 0, avgOrder: 0, growth: '+5.2%' },
        { name: 'Tuần này', revenue: data.weekRevenue, orders: 0, avgOrder: 0, growth: '+12.8%' },
        { name: 'Tháng này', revenue: data.monthRevenue, orders: 0, avgOrder: 0, growth: '+8.5%' },
        { name: 'Năm nay', revenue: data.yearRevenue, orders: 0, avgOrder: 0, growth: '+15.3%' }
    ];
    
    periods.forEach(function(period) {
        const row = `
            <tr>
                <td>${period.name}</td>
                <td>${formatCurrency(period.revenue)}</td>
                <td>${period.orders}</td>
                <td>${formatCurrency(period.avgOrder)}</td>
                <td><span class="text-success">${period.growth}</span></td>
            </tr>
        `;
        tableBody.append(row);
    });
}

/**
 * Display top products cards
 */
function displayTopProductsCards(products) {
    const cardsContainer = $('#topProductsCards');
    if (!cardsContainer.length) return;
    
    cardsContainer.empty();
    
    products.slice(0, 4).forEach(function(product, index) {
        const card = `
            <div class="col-xl-3 col-sm-6 col-12">
                <div class="card">
                    <div class="card-body">
                        <div class="dash-widget-header">
                            <span class="dash-widget-icon text-primary">
                                <span class="badge bg-primary">#${product.rank}</span>
                            </span>
                            <div class="dash-count">
                                <h3>${product.productName}</h3>
                            </div>
                        </div>
                        <div class="dash-widget-info">
                            <h6 class="text-muted">Số lượng: ${product.totalQuantitySold}</h6>
                            <h6 class="text-success">Doanh thu: ${formatCurrency(product.totalRevenue)}</h6>
                        </div>
                    </div>
                </div>
            </div>
        `;
        cardsContainer.append(card);
    });
}

/**
 * Display top products table
 */
function displayTopProductsTable(products) {
    const tableBody = $('#topProductsTable');
    if (!tableBody.length) return;
    
    tableBody.empty();
    
    products.forEach(function(product) {
        const row = `
            <tr>
                <td>
                    <span class="badge bg-primary">#${product.rank}</span>
                </td>
                <td>
                    <strong>${product.productName}</strong>
                </td>
                <td>
                    <img src="${product.imageUrl || 'assets/images/product-placeholder.jpg'}" 
                         alt="${product.productName}" 
                         class="img-fluid" 
                         style="width: 50px; height: 50px; object-fit: cover;">
                </td>
                <td>
                    <span class="badge bg-info">${product.totalQuantitySold}</span>
                </td>
                <td>
                    <span class="text-success fw-bold">${formatCurrency(product.totalRevenue)}</span>
                </td>
                <td>
                    <span class="text-success">+${Math.floor(Math.random() * 20 + 5)}%</span>
                </td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" onclick="viewProductDetails(${product.productId})">
                        <i class="fas fa-eye"></i> Xem chi tiết
                    </button>
                </td>
            </tr>
        `;
        tableBody.append(row);
    });
}

/**
 * Update top products statistics
 */
function updateTopProductsStats(products) {
    const totalRevenue = products.reduce((sum, p) => sum + parseFloat(p.totalRevenue), 0);
    const totalQuantity = products.reduce((sum, p) => sum + p.totalQuantitySold, 0);
    const bestSeller = products.length > 0 ? products[0].productName : '-';
    
    updateElement('#totalTopRevenue', formatCurrency(totalRevenue));
    updateElement('#totalTopQuantity', totalQuantity);
    updateElement('#bestSeller', bestSeller);
}

/**
 * Update delivery performance table
 */
function updateDeliveryPerformanceTable() {
    const tableBody = $('#deliveryPerformanceTable');
    if (!tableBody.length) return;
    
    tableBody.empty();
    
    const areas = [
        { name: 'Hà Nội', total: 45, success: 42, failed: 3, rate: 93.3, avgTime: '2.1h' },
        { name: 'TP.HCM', total: 38, success: 35, failed: 3, rate: 92.1, avgTime: '2.8h' },
        { name: 'Đà Nẵng', total: 25, success: 23, failed: 2, rate: 92.0, avgTime: '2.3h' },
        { name: 'Cần Thơ', total: 18, success: 16, failed: 2, rate: 88.9, avgTime: '3.2h' }
    ];
    
    areas.forEach(function(area) {
        const row = `
            <tr>
                <td>${area.name}</td>
                <td>${area.total}</td>
                <td><span class="text-success">${area.success}</span></td>
                <td><span class="text-danger">${area.failed}</span></td>
                <td><span class="text-primary">${area.rate}%</span></td>
                <td>${area.avgTime}</td>
            </tr>
        `;
        tableBody.append(row);
    });
}

/**
 * Update recent deliveries table
 */
function updateRecentDeliveriesTable() {
    const tableBody = $('#recentDeliveriesTable');
    if (!tableBody.length) return;
    
    tableBody.empty();
    
    const deliveries = [
        { id: '12345', customer: 'Nguyễn Văn A', address: 'Hà Nội', status: 'Thành công', time: '2.1h' },
        { id: '12344', customer: 'Trần Thị B', address: 'TP.HCM', status: 'Đang giao', time: '1.8h' },
        { id: '12343', customer: 'Lê Văn C', address: 'Đà Nẵng', status: 'Thất bại', time: '3.2h' },
        { id: '12342', customer: 'Phạm Thị D', address: 'Cần Thơ', status: 'Thành công', time: '2.9h' }
    ];
    
    deliveries.forEach(function(delivery) {
        const statusClass = delivery.status === 'Thành công' ? 'text-success' : 
                          delivery.status === 'Thất bại' ? 'text-danger' : 'text-warning';
        
        const row = `
            <tr>
                <td>#${delivery.id}</td>
                <td>${delivery.customer}</td>
                <td>${delivery.address}</td>
                <td><span class="${statusClass}">${delivery.status}</span></td>
                <td>${delivery.time}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary">
                        <i class="fas fa-eye"></i> Xem chi tiết
                    </button>
                </td>
            </tr>
        `;
        tableBody.append(row);
    });
}

/**
 * Update element content
 */
function updateElement(selector, content) {
    const element = $(selector);
    if (element.length) {
        element.text(content);
    }
}

/**
 * Format currency
 */
function formatCurrency(amount) {
    if (!amount) return '0 VNĐ';
    
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}

/**
 * Show loading state
 */
function showLoading() {
    $('.stats-card, .card').addClass('loading');
}

/**
 * Hide loading state
 */
function hideLoading() {
    $('.stats-card, .card').removeClass('loading');
}

/**
 * Show error message
 */
function showError(message) {
    // Create toast notification
    const toast = `
        <div class="toast-container position-fixed top-0 end-0 p-3">
            <div class="toast align-items-center text-white bg-danger border-0" role="alert">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="fas fa-exclamation-triangle"></i> ${message}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </div>
    `;
    
    $('body').append(toast);
    
    // Show toast
    const toastElement = $('.toast');
    const bsToast = new bootstrap.Toast(toastElement[0]);
    bsToast.show();
    
    // Remove toast after it's hidden
    toastElement.on('hidden.bs.toast', function() {
        $(this).parent().remove();
    });
}

/**
 * Setup auto refresh
 */
function setupAutoRefresh() {
    // Clear existing interval
    if (refreshInterval) {
        clearInterval(refreshInterval);
    }
    
    // Set new interval (5 minutes)
    refreshInterval = setInterval(function() {
        console.log('Auto refreshing statistics...');
        loadQuickStats();
    }, 300000); // 5 minutes
}

/**
 * Initialize tooltips
 */
function initTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

/**
 * Setup event listeners
 */
function setupEventListeners() {
    // Period button clicks
    $('.btn-group .btn').on('click', function() {
        $('.btn-group .btn').removeClass('btn-primary').addClass('btn-outline-primary');
        $(this).removeClass('btn-outline-primary').addClass('btn-primary');
    });
    
    // Form control changes
    $('#limitSelect, #sortBy').on('change', function() {
        const period = currentPeriod;
        const limit = $('#limitSelect').val() || 10;
        
        if (window.location.pathname.includes('top-products')) {
            loadTopProducts(period, limit);
        }
    });
    
    // Window resize
    $(window).on('resize', function() {
        // Update charts if they exist
        if (window.revenueChart) {
            window.revenueChart.resize();
        }
        if (window.topProductsChart) {
            window.topProductsChart.resize();
        }
    });
}

/**
 * View product details
 */
function viewProductDetails(productId) {
    window.location.href = `product-detail.html?id=${productId}`;
}

/**
 * Export report
 */
function exportReport() {
    // Show loading
    showLoading();
    
    // Simulate export process
    setTimeout(function() {
        hideLoading();
        
        // Create download link
        const link = document.createElement('a');
        link.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent('Report Data');
        link.download = `report-${new Date().toISOString().split('T')[0]}.csv`;
        link.click();
        
        showSuccess('Báo cáo đã được xuất thành công!');
    }, 2000);
}

/**
 * Show success message
 */
function showSuccess(message) {
    const toast = `
        <div class="toast-container position-fixed top-0 end-0 p-3">
            <div class="toast align-items-center text-white bg-success border-0" role="alert">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="fas fa-check-circle"></i> ${message}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </div>
    `;
    
    $('body').append(toast);
    
    const toastElement = $('.toast');
    const bsToast = new bootstrap.Toast(toastElement[0]);
    bsToast.show();
    
    toastElement.on('hidden.bs.toast', function() {
        $(this).parent().remove();
    });
}

/**
 * Open settings
 */
function openSettings() {
    alert('Tính năng cài đặt sẽ được phát triển sau!');
}

/**
 * Show help
 */
function showHelp() {
    const helpText = `
Hướng dẫn sử dụng hệ thống thống kê:

1. Dashboard: Xem tổng quan tất cả thống kê
2. Doanh thu: Phân tích doanh thu theo thời gian
3. Đơn hàng: Theo dõi trạng thái và hiệu suất đơn hàng
4. Top sản phẩm: Xem sản phẩm bán chạy nhất
5. Giao hàng: Báo cáo hiệu suất giao hàng

Dữ liệu được cập nhật tự động mỗi 5 phút.
    `;
    
    alert(helpText);
}

/**
 * Cleanup on page unload
 */
$(window).on('beforeunload', function() {
    if (refreshInterval) {
        clearInterval(refreshInterval);
    }
});

// Initialize when document is ready
$(document).ready(function() {
    initStatsDashboard();
}); 