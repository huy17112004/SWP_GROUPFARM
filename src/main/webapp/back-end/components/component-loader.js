// ... existing code ...
function loadComponent(url, placeholderId, selector) {
    return fetch(url)
        .then(response => response.text())
        .then(data => {
            const placeholder = document.getElementById(placeholderId);
            if (!placeholder) return;
            const parser = new DOMParser();
            const doc = parser.parseFromString(data, 'text/html');
            const content = doc.querySelector(selector);
            if (content) placeholder.replaceWith(content);
        });
}

function loadModal(url, selector) {
    return fetch(url)
        .then(response => response.text())
        .then(data => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(data, 'text/html');
            const modal = doc.querySelector(selector);
            if (modal) {
                // Check if modal already exists
                const existingModal = document.querySelector(selector);
                if (!existingModal) {
                    document.body.appendChild(modal);
                }
            }
        });
}

document.addEventListener('DOMContentLoaded', function () {
    // Load header
    loadComponent('components/header.html', 'header-placeholder', '.page-header')
        .then(() => {
            if (typeof feather !== 'undefined') {
                feather.replace();
            }
        });
    
    // Load logout modal
    loadModal('components/header.html', '#staticBackdrop');
});

$(document).on("click", ".mode", function () {
    $('body').toggleClass("dark-only");
});
// ... existing code ...