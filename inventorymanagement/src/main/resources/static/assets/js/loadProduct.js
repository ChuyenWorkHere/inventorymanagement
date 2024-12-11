$(document).ready(function () {
    $.ajax({
        url: '/products/',
        type: 'GET',
        success: function (data) {
            let productHtml = '';
            data.forEach((product, index) => {
                productHtml += `
                <tr>
                    <td>
                        <label class="checkboxs">
                            <input type="checkbox" data-id="${product.productId}">
                            <span class="checkmarks"></span>
                        </label>
                    </td>
                    <td>${product.productId}</td>
                    <td>${product.name}</td>
                    <td>${product.productDescription || 'Không có'}</td>
                    <td>${product.category.categoryName}</td>
                    <td>${product.unit}</td>
                    <td>${product.quantity}</td>
                    <td>
                        <input type="number" class="number" min="0" max="${product.quantity}">
                    </td>
                </tr>
                `;
            });

            $('#table-product-list').html(productHtml);
        },
        error: function (xhr) {
            console.error("Lỗi khi tải danh sách sản phẩm:", xhr);
            alert("Không thể tải danh sách sản phẩm.");
        }
    });

    // Chức năng chọn tất cả sản phẩm
    $('#select-all').on('change', function () {
        const isChecked = $(this).is(':checked');
        $('#product-list input[type="checkbox"]').prop('checked', isChecked);
    });
});
