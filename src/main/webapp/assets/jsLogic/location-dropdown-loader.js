// location-dropdown-loader.js
// Tải dropdown tỉnh, huyện, xã từ API LocationServlet

export function setupLocationDropdown({ provinceId = 'province', districtId = 'district', wardId = 'ward' } = {}) {
    const provinceSelect = document.getElementById(provinceId);
    const districtSelect = document.getElementById(districtId);
    const wardSelect = document.getElementById(wardId);

    if (!provinceSelect || !districtSelect || !wardSelect) return;

    provinceSelect.addEventListener('change', function() {
        loadDistricts(this.value);
    });
    districtSelect.addEventListener('change', function() {
        loadWards(this.value);
    });

    async function loadProvinces() {
        provinceSelect.innerHTML = '<option value="">Chọn tỉnh/thành phố</option>';
        districtSelect.innerHTML = '<option value="">Chọn quận/huyện</option>';
        districtSelect.disabled = true;
        wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';
        wardSelect.disabled = true;
        try {
            const res = await fetch('/provinces');
            const data = await res.json();
            data.forEach(province => {
                const option = document.createElement('option');
                option.value = province.locationId;
                option.textContent = province.locationName;
                provinceSelect.appendChild(option);
            });
        } catch (e) {
            alert('Không thể tải danh sách tỉnh/thành phố');
        }
    }

    async function loadDistricts(provinceId) {
        districtSelect.innerHTML = '<option value="">Chọn quận/huyện</option>';
        wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';
        wardSelect.disabled = true;
        if (!provinceId) {
            districtSelect.disabled = true;
            return;
        }
        try {
            const res = await fetch(`/districts?provinceId=${provinceId}`);
            const data = await res.json();
            data.forEach(district => {
                const option = document.createElement('option');
                option.value = district.locationId;
                option.textContent = district.locationName;
                districtSelect.appendChild(option);
            });
            districtSelect.disabled = false;
        } catch (e) {
            alert('Không thể tải danh sách quận/huyện');
        }
    }

    async function loadWards(districtId) {
        wardSelect.innerHTML = '<option value="">Chọn phường/xã</option>';
        if (!districtId) {
            wardSelect.disabled = true;
            return;
        }
        try {
            const res = await fetch(`/wards?districtId=${districtId}`);
            const data = await res.json();
            data.forEach(ward => {
                const option = document.createElement('option');
                option.value = ward.locationId;
                option.textContent = ward.locationName;
                wardSelect.appendChild(option);
            });
            wardSelect.disabled = false;
        } catch (e) {
            alert('Không thể tải danh sách phường/xã');
        }
    }

    // Public API
    return {
        loadProvinces
    };
} 