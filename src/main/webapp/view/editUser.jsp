<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="row col-12 m-3">

    <div class="form-img col-md-3 col-12 px-4">
      <div class="col-12 shadow-lg ">
      	<img class="w-100" id="previewImg" src="${ctx}imgs/${emptyUser.image}" alt="Ảnh đại diện" >
      </div>
    </div>
    <form id="userForm" action="${ctx}admin/${editU? 'updateUser':'inserUser'}" enctype="multipart/form-data" method="post" class="form_main col-md-9 p-3 col-12 bg-white shadow-lg position-relative overflow-hidden">
      <p class="heading z-2 text-center">Tạo Tài Khoản</p>

      <div class="row">
        <div class="col-6 px-5">
          <div class="inputContainer">
            <i class="fas fa-id-card inputIcon"></i>
            <input type="text" class="inputField" name="fullname" placeholder="Họ Tên" value="${emptyUser.fullname}">
          </div>

          <div class="inputContainer">
            <i class="fas fa-lock inputIcon"></i>
            <input type="password" class="inputField" name="password" placeholder="Mật khẩu" value="${emptyUser.password}">
          </div>

          <div class="inputContainer">
            <i class="fas fa-image inputIcon"></i>
            <input id="imageIPfile" type="file" class="inputField" name="image">
            <input type="hidden" value="${emptyUser.image}" class="d-none" name="imgCu">
          </div>
        </div>

        <div class="col-6 px-5">
          <div class="inputContainer">
            <i class="fas fa-phone inputIcon"></i>
            <input type="text" class="inputField" name="phone" placeholder="Số điện thoại" value="${emptyUser.phone}">
          </div>

          <div class="inputContainer">
            <i class="fa-solid inputIcon fa-envelope"></i>
            <input type="text" class="inputField" name="email" placeholder="Email" value="${emptyUser.email}">
          </div>

          <div class="inputContainer">
            <i class="fas fa-briefcase inputIcon"></i>
            <select class="inputField" name="admin">
              <option value="true" ${emptyUser.admin ? 'selected' : ''}>Admin</option>
              <option value="false" ${!emptyUser.admin ? 'selected' : ''}>User</option>
            </select>
          </div>

          <div class="inputContainer radioContainer">
            <label>
              <input type="radio" name="gender" value="true" ${emptyUser.gender ? 'checked' : ''}> Nam
            </label>
            <label>
              <input type="radio" name="gender" value="false" ${!emptyUser.gender ? 'checked' : ''}> Nữ
            </label>
          </div>
        </div>
      </div>

      <div class="row my-3 d-flex justify-content-center">
        <div class="col-3">
          <input type="text" name="id" class="inputField" id="id" value="${emptyUser.id}" placeholder="ID">
        </div>

        <button  class="col-3" type="submit" id="button">${editU ? 'Cập nhập':'Tạo Tài Khoản'}</button>
      </div>
    </form>
    
    
    
    
</div>
<div class="toast-container position-fixed top-0 end-0 p-3">
  <div id="successToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="d-flex">
      <div id="toatThongbao" class="toast-body">
      </div>
      
    </div>
  </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('userForm');
    form.addEventListener('submit', async (event) => {
        console.log("ádasdasdas");
        event.preventDefault();
        const formData = new FormData(form);
        const data = {};

        var inputFile = document.getElementById("imageIPfile");
        formData.append('photo', inputFile.files[0]);

        for (let [key, value] of formData.entries()) {
            if (key !== 'image') {
                data[key] = value;
            }
        }
        try {
            fetch(form.action, {
                method: form.method,
                
                body: formData,
            })
            .then(response => response.json())
            .then(json => {
            	if (form.method != 'post'){
            		document.getElementById('toatThongbao').textContent = "Tạo tài khoản thành công!";
               	}else {
               		document.getElementById('toatThongbao').textContent = "Cập nhập thành công!";
              	}
            	
                
                var successToast = new bootstrap.Toast(document.getElementById('successToast'));
                successToast.show();

                console.log(json);

                document.querySelector('input[name="fullname"]').value = json.fullname;
                document.querySelector('input[name="password"]').value = json.password;
                document.querySelector('input[name="phone"]').value = json.phone;
                document.querySelector('input[name="email"]').value = json.email;
                document.querySelector('input[name="id"]').value = json.id;
                var imgPreview = document.getElementById("previewImg");
                const src = json.image;
                imgPreview.src = `${ctx}imgs/` + src;

                console.log(`${ctx}imgs/` + src);
            })
            .catch(error => {
                console.error('Error:', error);
            });
        } catch (error) {
            console.error('Error in try block:', error);
        }
    });
});
</script>

