export async function fetchAddressCustomer() {
    const res = await fetch('/api/customer-addresses');
    if (!res.ok) throw new Error(`Fetch lỗi: ${res.status}`);
    return await res.json();
}

export function renderAddressCustomer(addresses) {
    const adds = document.getElementById('address-list');
    if (!adds) return;
    adds.innerHTML = '';

    addresses.forEach(addr => {
        // Tạo <li>
        const add = document.createElement('div');
        add.className = "col-xxl-6 col-lg-12 col-md-6";
        // Build inner HTML
        add.innerHTML = `
                                                    <div class="delivery-address-box">
                                                        <div>
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" name="addressCustomerId" value="${addr.id}">
                                                            </div>

                                                            <ul class="delivery-address-detail">

                                                                <li>
                                                                    <p class="text-content"><span
                                                                            class="text-title">Địa chỉ
                                                                            : </span>${addr.street}, ${addr.wardName}, ${addr.districtName}, ${addr.provinceName}</p>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
    `;
        adds.appendChild(add);
    });
}