// ==============================
// Tamils Fashion - Multiple Billing JS
// ==============================

document.addEventListener("DOMContentLoaded", function () {

    const product = document.getElementById("product");
    const quantity = document.getElementById("quantity");
    const price = document.getElementById("price");
    const total = document.getElementById("total");

    const addBtn = document.getElementById("addItem");
    const tbody = document.querySelector("#billTable tbody");
    const hiddenItems = document.getElementById("hiddenItems");
    const grandTotal = document.getElementById("grandTotal");

    let items = [];

    function calculate() {

        const option = product.options[product.selectedIndex];

        if (option.value === "") {

            price.value = "";
            total.value = "";

            return;
        }

        const p = parseFloat(option.getAttribute("data-price"));
        const q = parseInt(quantity.value);

        price.value = p.toFixed(2);
        total.value = (p * q).toFixed(2);

    }

    product.addEventListener("change", calculate);
    quantity.addEventListener("input", calculate);

    calculate();

    addBtn.addEventListener("click", function () {

        if (product.value === "") {

            alert("Select Product");
            return;

        }

        const option = product.options[product.selectedIndex];

        const productId = option.value;
        const productName = option.text;
        const productPrice = parseFloat(option.getAttribute("data-price"));
        const qty = parseInt(quantity.value);

        const subTotal = productPrice * qty;

        items.push({
            productId,
            qty,
            productPrice,
            subTotal,
            productName
        });

        renderTable();

        product.selectedIndex = 0;
        quantity.value = 1;

        calculate();

    });

    function renderTable() {

        tbody.innerHTML = "";
        hiddenItems.innerHTML = "";

        let grand = 0;

        items.forEach(function (item, index) {

            grand += item.subTotal;

            tbody.innerHTML += `
                <tr>
                    <td>${item.productName}</td>
                    <td>${item.qty}</td>
                    <td>${item.productPrice.toFixed(2)}</td>
                    <td>${item.subTotal.toFixed(2)}</td>
                    <td>
                        <button type="button"
                                onclick="removeItem(${index})">
                            Remove
                        </button>
                    </td>
                </tr>
            `;

            hiddenItems.innerHTML += `
                <input type="hidden"
                       name="items[${index}].productId"
                       value="${item.productId}">

                <input type="hidden"
                       name="items[${index}].quantity"
                       value="${item.qty}">
            `;

        });

        grandTotal.innerText = grand.toFixed(2);

    }

    window.removeItem = function (index) {

        items.splice(index, 1);

        renderTable();

    };

});