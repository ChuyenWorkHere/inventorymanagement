$(document).ready(function () {
    $.ajax({
            url: '/home',
            method: 'GET',
            success: function (response) {
            },
            error: function (xhr) {
            }
    });
    $.ajax({
            url: '/account/detail/',
            type: 'GET',
            success: function (data) {
                $('#user-name').text(data.fullName || 'Unknown User');

                if(data.role === "QUANTRI"){
                    $('#user-role').text("Quản trị viên")
                } else if(data.role === "NHANVIEN") {
                    $('#user-role').text("Nhân viên kho")
                } else if (data.role === "QUANLY") {
                    $('#user-role').text("Quản lý kho")
                } else {
                    $('#user-role').text("Unknown Role")
                }
            },
            error: function (xhr) {
                if(xhr.status === 401){
                    alert("Bạn chưa đăng nhập");
                    window.location.href = xhr.responseText;
                }

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

