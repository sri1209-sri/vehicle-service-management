// Author: Devadarshini M
/**
 * Vehicle Service Management Common JS Utilities
 */

// Toast Notifications System
function showToast(message, type = 'success') {
    let container = document.querySelector('.toast-container');
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    
    let icon = '\u2713';
    if (type === 'danger') icon = '\u2717';
    if (type === 'warning') icon = '\u26A0';

    toast.innerHTML = `
        <span style="font-weight:bold; font-size:1.2rem;">${icon}</span>
        <div>${message}</div>
    `;
    container.appendChild(toast);

    // Fade out and remove
    setTimeout(() => {
        toast.style.transition = 'opacity 0.5s ease-out, transform 0.5s ease-out';
        toast.style.opacity = '0';
        toast.style.transform = 'translateY(-20px)';
        setTimeout(() => toast.remove(), 500);
    }, 4000);
}

// Modal System
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
    }
}

// REST API Helper
async function apiRequest(url, method = 'GET', data = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };
    if (data) {
        options.body = JSON.stringify(data);
    }
    
    try {
        const response = await fetch(url, options);
        if (!response.ok) {
            const errorText = await response.text();
            let parsedError = null;
            try {
                parsedError = JSON.parse(errorText);
            } catch (e) {
                // Not JSON
            }
            
            if (parsedError) {
                if (parsedError.errors && typeof parsedError.errors === 'object') {
                    const messages = Object.values(parsedError.errors).join(', ');
                    throw new Error(messages || parsedError.message || 'Validation failed');
                } else if (parsedError.message) {
                    throw new Error(parsedError.message);
                }
            }
            throw new Error(errorText || `HTTP error! Status: ${response.status}`);
        }
        
        // Some endpoints return a plain string instead of JSON
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            return await response.text();
        }
    } catch (error) {
        console.error('API Error:', error);
        showToast(error.message, 'danger');
        throw error;
    }
}

// Form Helpers
function serializeForm(formId) {
    const form = document.getElementById(formId);
    if (!form) return {};
    const formData = new FormData(form);
    const obj = {};
    formData.forEach((value, key) => {
        // Handle nested properties or convert types
        if (key.includes('.')) {
            const parts = key.split('.');
            let temp = obj;
            for (let i = 0; i < parts.length; i++) {
                const part = parts[i];
                if (i === parts.length - 1) {
                    temp[part] = value;
                } else {
                    temp[part] = temp[part] || {};
                    temp = temp[part];
                }
            }
        } else {
            // Check if it's a numeric field
            if (value && !isNaN(value) && form.querySelector(`[name="${key}"]`).type === 'number') {
                obj[key] = Number(value);
            } else {
                obj[key] = value;
            }
        }
    });
    return obj;
}

// Bind Escape key and window clicks to close modals
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        const activeModals = document.querySelectorAll('.modal.active');
        activeModals.forEach(modal => modal.classList.remove('active'));
    }
});

document.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal')) {
        e.target.classList.remove('active');
    }
});