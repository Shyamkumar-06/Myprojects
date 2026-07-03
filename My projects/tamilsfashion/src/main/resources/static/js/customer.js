// ==============================
// Tamils Fashion - Customer JS
// ==============================

document.addEventListener("DOMContentLoaded", function () {

    // Search Filter
    const searchInput = document.getElementById("searchInput");
    const table = document.getElementById("customerTable");

    if (searchInput && table) {

        searchInput.addEventListener("keyup", function () {

            const filter = this.value.toLowerCase();
            const rows = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");

            for (let i = 0; i < rows.length; i++) {

                const text = rows[i].innerText.toLowerCase();

                if (text.indexOf(filter) > -1) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }

            }

        });

    }

});

// Delete Confirmation
function confirmDelete() {

    return confirm("Are you sure you want to delete this customer?");

}