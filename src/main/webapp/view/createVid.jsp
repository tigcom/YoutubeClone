<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row col-12 m-3">
    <div class="form-img col-md-3 col-12 px-4">
      <div class="col-12 shadow-lg ">
      	<img class="w-100" id="previewImg" src="${creVid.poster}" alt="Ảnh poster" >
      </div>
    </div>
    <form action="${ctx}/admin/${editV? 'updateVideo':'insertVideo'}" enctype="multipart/form-data" method="post" class="form_main col-md-9 p-3 col-12 bg-white shadow-lg position-relative overflow-hidden">
      <p class="heading z-2 text-center">${editV ? 'Chỉnh sửa video':'Tạo mới video'}</p>

      <div class="row">
        <div class="col-6 px-5">
       	  <div class="inputContainer">
            <i class="fa-regular fa-images inputIcon"></i>
            <input id="imgPoster" type="text" class="inputField" name="poster" placeholder="Ảnh poster" value="${creVid.poster}">
          </div>
          <div class="inputContainer">
            <i class="fa-solid fa-pager inputIcon"></i>
            <input type="text" class="inputField" name="title" placeholder="Tiêu đề" value="${creVid.title}">
          </div>

          <div class="inputContainer">
            <i class="fa-regular fa-file-video inputIcon"></i>
            <input type="text" class="inputField" name="link" placeholder="Link iframe" value="${creVid.link}">
          </div>
        </div>

        <div class="col-6 px-5">
		  <div class="inputContainer">
            <i class="fa-solid fa-file-invoice inputIcon"></i>
            <textarea placeholder="Mô tả" name="description" class="inputField">${creVid.description}</textarea>
          </div>
          <div class="inputContainer d-flex">
            <i class="fa-solid fa-eye inputIcon"></i>
            <p class="ttp">Trạng thái</p>
            <input type="checkbox" class="inputField" name="active" value="true" ${creVid.active ? 'checked':''}> 
          </div>

        
        </div>
      </div>

      <div class="row my-3 d-flex justify-content-center">
        <div class="col-3">
          <input type="text" name="id" class="inputField" id="id" value="${creVid.id}" placeholder="ID">
        </div>

        <button   class="col-3" type="submit" id="button">${editV ? 'Cập nhập':'Tạo Video'}</button>
      </div>
    </form>
</div>
	<script type="text/javascript">
		document.getElementById('imgPoster').addEventListener('input', function(event) {
  document.getElementById('previewImg').src = event.target.value; 
});
	</script>
