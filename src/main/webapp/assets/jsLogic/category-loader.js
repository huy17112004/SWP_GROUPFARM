export async function fetchCategories() {
    const res = await fetch('/api/categories');
    if (!res.ok) throw new Error(`Fetch lỗi: ${res.status}`);
    return await res.json();  // mảng { id, categoryName }
}

export function renderCategories(categories) {
    const ul = document.getElementById('category-list');
    if (!ul) return;
    ul.innerHTML = '';

    categories.forEach(cat => {
        // Tạo <li>
        const li = document.createElement('li');

        // Build inner HTML
        li.innerHTML = `
      <div class="category-list text-center">
        <img 
          src="../assets/svg/categories/category_${cat.id}.svg"
          alt="${cat.categoryName}" 
          class="blur-up lazyload mb-2"
        >
        <h5>
          <a href="shop-category.html?categoryId=${cat.id}">
            ${cat.categoryName}
          </a>
        </h5>
      </div>
    `;
        ul.appendChild(li);
    });
}
export function renderCategoriesInShopCategory(categories) {
    const ul = document.getElementById('category-list');
    if (!ul) return;
    ul.innerHTML = '';

    categories.forEach((cat,idx) => {
        const isActive = idx === 0;
        // Tạo <li>
        const li = document.createElement('li');
        li.className = 'nav-item';
        li.setAttribute('role', 'presentation');
        // Build inner HTML
        li.innerHTML = `
      <button class="nav-link${isActive ? ' active' : ''}"
      id="pills-${cat.id}-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-${cat.id}"
      type="button"
      role="tab"
      aria-controls="pills-${cat.id}"
      aria-selected="${isActive}"
      data-category-id="${cat.id}"
    >${cat.categoryName}
                                        <img src="../assets/svg/categories/category_${cat.id}.svg" class="blur-up lazyload"
                                            alt="${cat.categoryName}"></button>
    `;
        ul.appendChild(li);
    });
}