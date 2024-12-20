// Global variables
let products = [];
let sales = [];

// Initialize the application
document.addEventListener('DOMContentLoaded', () => {
    loadProducts();
});

// Navigation functions
function showProducts() {
    document.getElementById('products-section').style.display = 'block';
    document.getElementById('sales-section').style.display = 'none';
    document.getElementById('low-stock-section').style.display = 'none';
    loadProducts();
}

function showSales() {
    document.getElementById('products-section').style.display = 'none';
    document.getElementById('sales-section').style.display = 'block';
    document.getElementById('low-stock-section').style.display = 'none';
    loadSales();
}

function showLowStock() {
    document.getElementById('products-section').style.display = 'none';
    document.getElementById('sales-section').style.display = 'none';
    document.getElementById('low-stock-section').style.display = 'block';
    loadLowStockProducts();
}

// Product functions
async function loadProducts() {
    try {
        const response = await fetch('/api/products');
        products = await response.json();
        displayProducts();
    } catch (error) {
        console.error('Error loading products:', error);
        alert('Erro ao carregar produtos');
    }
}

function displayProducts() {
    const tbody = document.getElementById('products-table-body');
    tbody.innerHTML = '';
    
    products.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>R$ ${product.price.toFixed(2)}</td>
            <td>${product.quantity}</td>
            <td>${product.minimumStock}</td>
            <td>
                <button class="btn btn-sm btn-primary btn-action" onclick="editProduct(${product.id})">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-danger btn-action" onclick="deleteProduct(${product.id})">
                    <i class="bi bi-trash"></i>
                </button>
                <button class="btn btn-sm btn-success btn-action" onclick="updateStock(${product.id})">
                    <i class="bi bi-box"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function showAddProductModal() {
    document.getElementById('addProductForm').reset();
    const modal = new bootstrap.Modal(document.getElementById('addProductModal'));
    modal.show();
}

async function saveProduct() {
    const product = {
        name: document.getElementById('productName').value,
        description: document.getElementById('productDescription').value,
        price: parseFloat(document.getElementById('productPrice').value),
        quantity: parseInt(document.getElementById('productQuantity').value),
        minimumStock: parseInt(document.getElementById('productMinStock').value)
    };

    try {
        const response = await fetch('/api/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        });

        if (response.ok) {
            bootstrap.Modal.getInstance(document.getElementById('addProductModal')).hide();
            loadProducts();
        } else {
            alert('Erro ao salvar produto');
        }
    } catch (error) {
        console.error('Error saving product:', error);
        alert('Erro ao salvar produto');
    }
}

async function deleteProduct(id) {
    if (!confirm('Tem certeza que deseja excluir este produto?')) {
        return;
    }

    try {
        const response = await fetch(`/api/products/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadProducts();
        } else {
            alert('Erro ao excluir produto');
        }
    } catch (error) {
        console.error('Error deleting product:', error);
        alert('Erro ao excluir produto');
    }
}

// Sales functions
async function loadSales() {
    try {
        const response = await fetch('/api/sales');
        sales = await response.json();
        displaySales();
    } catch (error) {
        console.error('Error loading sales:', error);
        alert('Erro ao carregar vendas');
    }
}

function displaySales() {
    const tbody = document.getElementById('sales-table-body');
    tbody.innerHTML = '';
    
    sales.forEach(sale => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${new Date(sale.saleDate).toLocaleString()}</td>
            <td>${sale.items.length} itens</td>
            <td>R$ ${sale.totalAmount.toFixed(2)}</td>
            <td>${formatPaymentMethod(sale.paymentMethod)}</td>
            <td>
                <button class="btn btn-sm btn-info btn-action" onclick="viewSaleDetails(${sale.id})">
                    <i class="bi bi-eye"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function formatPaymentMethod(method) {
    const methods = {
        CASH: 'Dinheiro',
        CREDIT_CARD: 'Cartão de Crédito',
        DEBIT_CARD: 'Cartão de Débito',
        PIX: 'PIX'
    };
    return methods[method] || method;
}

function showNewSaleModal() {
    document.getElementById('newSaleForm').reset();
    document.getElementById('saleItems').innerHTML = '';
    addSaleItem(); // Add first item row
    const modal = new bootstrap.Modal(document.getElementById('newSaleModal'));
    modal.show();
}

function addSaleItem() {
    const container = document.getElementById('saleItems');
    const itemDiv = document.createElement('div');
    itemDiv.className = 'sale-item mb-3';
    itemDiv.innerHTML = `
        <div class="row">
            <div class="col-md-5">
                <label class="form-label">Produto</label>
                <select class="form-select product-select" required>
                    ${products.map(p => `<option value="${p.id}">${p.name} - R$ ${p.price.toFixed(2)}</option>`).join('')}
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Quantidade</label>
                <input type="number" class="form-control quantity-input" required onchange="updateSubtotal(this)">
            </div>
            <div class="col-md-3">
                <label class="form-label">Subtotal</label>
                <input type="text" class="form-control" readonly>
            </div>
            <div class="col-md-1">
                <label class="form-label">&nbsp;</label>
                <button type="button" class="btn btn-danger btn-sm" onclick="removeSaleItem(this)">
                    <i class="bi bi-trash"></i>
                </button>
            </div>
        </div>
    `;
    container.appendChild(itemDiv);
}

function removeSaleItem(button) {
    button.closest('.sale-item').remove();
    updateTotal();
}

function updateSubtotal(quantityInput) {
    const row = quantityInput.closest('.row');
    const productId = row.querySelector('.product-select').value;
    const product = products.find(p => p.id === parseInt(productId));
    const quantity = parseInt(quantityInput.value);
    const subtotal = product.price * quantity;
    
    row.querySelector('input[readonly]').value = `R$ ${subtotal.toFixed(2)}`;
    updateTotal();
}

function updateTotal() {
    const subtotals = Array.from(document.querySelectorAll('.sale-item input[readonly]'))
        .map(input => parseFloat(input.value.replace('R$ ', '')) || 0);
    const total = subtotals.reduce((sum, value) => sum + value, 0);
    document.getElementById('saleTotal').value = `R$ ${total.toFixed(2)}`;
}

async function saveSale() {
    const items = Array.from(document.querySelectorAll('.sale-item')).map(item => {
        const productId = item.querySelector('.product-select').value;
        const quantity = parseInt(item.querySelector('.quantity-input').value);
        return { productId, quantity };
    });

    const sale = {
        items,
        paymentMethod: document.getElementById('paymentMethod').value
    };

    try {
        const response = await fetch('/api/sales', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(sale)
        });

        if (response.ok) {
            bootstrap.Modal.getInstance(document.getElementById('newSaleModal')).hide();
            showSales();
        } else {
            alert('Erro ao registrar venda');
        }
    } catch (error) {
        console.error('Error saving sale:', error);
        alert('Erro ao registrar venda');
    }
}

// Low stock functions
async function loadLowStockProducts() {
    try {
        const response = await fetch('/api/products/low-stock');
        const products = await response.json();
        displayLowStockProducts(products);
    } catch (error) {
        console.error('Error loading low stock products:', error);
        alert('Erro ao carregar produtos com estoque baixo');
    }
}

function displayLowStockProducts(products) {
    const tbody = document.getElementById('low-stock-table-body');
    tbody.innerHTML = '';
    
    products.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${product.name}</td>
            <td class="stock-warning">${product.quantity}</td>
            <td>${product.minimumStock}</td>
            <td>
                <button class="btn btn-sm btn-success btn-action" onclick="updateStock(${product.id})">
                    <i class="bi bi-box"></i> Atualizar Estoque
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}
