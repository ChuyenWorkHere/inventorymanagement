$(document).ready(function () {
    $.ajax({
            url: '/home',
            method: 'GET',
            success: function (response) {
            },
            error: function (xhr) {
            }
    });
    $('#logout').on('click', function () {
        console.log("Something");
        $.ajax({
            url: '/auth/logout',
            method: 'POST',
            success: function (response) {
            console.log("Success");
            window.location.href = response;
            },
            error: function (xhr) {
                alert('Đăng xuất thất bại: ' + xhr.responseText); // Hiển thị lỗi
            }
        });
    });
});