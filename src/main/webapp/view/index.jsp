
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fm"%>
<c:set var="ctx" value="${pageContext.request.contextPath}/"
	scope="request" />

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon"
	href="https://img-cache.coccoc.com/image2?i=2&l=19/306881396"
	type="image/x-icon">
<title>YouTube Fake</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/custom.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body class="position-relative" style="background-color: #f9f9f9;">
	<script type="text/javascript">let socket= null;</script>
	<nav
		class="navbar navbar-expand-lg bg-light navbar-dark sticky-top px-4">
		<c:if test="${chitiet}">
			<button class="btn " type="button" data-bs-toggle="collapse"
				data-bs-target="#collapsibleNavbar">
				<i class=" fa-solid fa-bars"></i>
			</button>
		</c:if>
		<a class="navbar-brand d-flex align-items-center" href="#"> <i
			class="fab fa-youtube text-danger fs-6 me-2"></i> <span
			class="text-dark fw-bold position-relative">
				YouTube&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <small> <small><small
						class="position-absolute fw-lighter text-secondary top-0 end-0"><small>Fake</small></small></small>
			</small>
		</span>
		</a>
		<form id="formSearchc" class="d-flex mx-auto w-50 btn-group"
			action="${ctx}index/video/search" method="post">
			<input name="search" id="searchInput" value="${keySearch}"
				class="form-control rounded-start-5"
				style="box-shadow: inset 0 2px 2px rgba(0, 0, 0, 0.1);"
				type="search" placeholder="Tìm kiếm" aria-label="Search">
			<button class="btn btn-outline-dark rounded-end-5" type="submit">
				<i class="fas fa-search"></i>
			</button>
			<button type="button" id="voiceSearchButton"
				class="btn bg-secondary bg-opacity-10 mx-2 text-dark rounded-5">
				<i class="fas fa-microphone"></i>
			</button>
		</form>
		<div class="d-flex">
			<c:choose>
				<c:when test="${empty sessionScope.idLogin}">
					<button class="btn btn-outline-dark" data-bs-toggle="modal"
						data-bs-target="#loginModal">Đăng nhập</button>
				</c:when>
				<c:otherwise>
					<a href="${ctx}/index/video/userview/protifle/${uLog.id}"
						class="btn btn-outline-dark">${uLog.fullname} </a>
				</c:otherwise>
			</c:choose>

		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div id="collapsibleNavbar"
				class="${chitiet ? 'position-fixed z-2 top-5 col-md-3  collapse shadow-sm':'col-md-2 p-0' }  bg-light py-3 border-end">
				<ul class="nav flex-column mb-3">
					<li class="nav-item"><a class="text-dark nav-link active"
						href="${ctx}index/video"> <i
							class="text-dark fas fa-home me-2"></i>Trang chủ
					</a></li>
					<li class="nav-item"><a class="nav-link text-dark"
						href="${ctx}index/video/trend"> <i class="fas fa-fire me-2"></i>Thịnh
							hành
					</a></li>
					<c:if test="${not empty sessionScope.idLogin}">
						<li class="nav-item"><a class="nav-link text-dark"
							href="${ctx}index/video/liked"> <i class="fas fa-play me-2"></i>Video
								đã thích
						</a></li>
					</c:if>
					<c:if test="${not empty sessionScope.idLogin}">
						<li class="nav-item"><a class="nav-link text-dark"
							href="${ctx}index/userview/visted"> <i
								class="fa-solid fa-clock-rotate-left"></i>Video đã xem
						</a></li>
					</c:if>
					<li class="nav-item"><a class="nav-link text-dark"
						href="${ctx}index/video/newVideo"> <i class="fas fa-bolt me-2"></i>Ngày
							đăng gần đây
					</a></li>
					<li class="nav-item"><a class="nav-link text-dark"
						href="${ctx}index/video/oldVideo"> <i
							class="fas fa-history me-2"></i>Ngày đăng cũ nhất
					</a></li>

				</ul>
				<hr>
				<h6>Danh mục</h6>
				<ul class="nav flex-column">
					<li class="nav-item"><a class="text-dark nav-link"
						href="${ctx}index/video/lab3"><i class="fa-solid fa-flask"></i>
							Lab 3</a></li>
					<c:if test="${not empty sessionScope.idLogin}">
						<li class="nav-item"><a class="text-dark nav-link"
							href="${ctx}index/video/logout"><i
								class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a></li>
					</c:if>
					<c:if test="${uLog.admin}">
						<li class="nav-item"><a class="nav-link text-dark"
							href="${ctx}admin/tableUser"><i class="fa-solid fa-users"></i>
								Quản lý người dùng</a></li>
						<li class="nav-item"><a class="nav-link text-dark"
							data-bs-toggle="modal" data-bs-target="#notiModal"><i
								class="fa-solid fa-triangle-exclamation"></i> Thông báo hệ thống
						</a></li>
						<li class="nav-item"><a class="nav-link text-dark"
							href="${ctx}admin/checklog"><i
								class="fa-solid fa-triangle-exclamation"></i> Lịch sử truy cập </a></li>
					</c:if>
				</ul>
			</div>

			<div class="col-md-10 ${chitiet ? 'col-md-12':'col-md-10'} py-4">
				<jsp:include page="${site}" />
			</div>
		</div>
	</div>

	<div class="modal fade " id="loginModal" tabindex="-1"
		aria-labelledby="loginModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="loginModalLabel">Đăng nhập</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<h4 class="text-warring ">${messss}</h4>
					<form method="post" action="${ctx}index/video/login">
						<div class="mb-3">
							<label class="form-label">Email</label> <input value="${mail}"
								type="text" class="form-control" name="id"
								placeholder="${empty feedBackMail? 'Nhập email của bạn':feedBackMail}">
						</div>
						<div class="mb-3">
							<label class="form-label">Mật khẩu</label> <input value="${pass}"
								type="password" class="form-control" name="password"
								placeholder="${empty feedBackPass? 'Nhập mật khẩu':feedBackPass}">
						</div>
						<button type="submit" class="btn btn-primary w-100">Đăng
							nhập</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade " id="notiModal" tabindex="-1"
		aria-labelledby="loginModalLabel" aria-hidden="false">
		<div class="modal-dialog">
			<form action="${ctx}admin/noti" method="post" class="modal-content">
				<div class="subscribe w-100 h-100 ">
					<p class="mt-3 mb-5">TẠO THÔNG BÁO</p>
					<input placeholder="Nội dung thông báo..." name="noti" type="text">
					<br>
					<button class="submit-btn">Gửi</button>
				</div>
			</form>
		</div>
	</div>
	<div style="display: ${checkNoti ? '':'none'};"
		class=" alert position-absolute alert-dismissible fade ${checkNoti ? 'show':''} cardN bg-light shadow-lg rounded-3"
		role="alert">
		<button type="button"
			class="btn-close position-absolute top-0 start-100"
			data-bs-dismiss="alert" aria-label="Close"></button>
		<div class="borderN"></div>
		<div class="content">
			<div class="logo">
				<div class="logo1">
					<i id="logo-main" class="fs-2 text-danger fa-regular fa-envelope"></i>
				</div>
				<div class="logo2 pb-2">
					<p id="logo-second" class="fs-5 text-danger fw-semibold pb-2">Thông
						báo</p>
				</div>
				<span class="trail"></span>
			</div>
			<span class="logo-bottom-text">${thongBaoOld}</span>
		</div>
		<span class="bottom-text">By Admin</span>
	</div>
	<div id="notiGM" class="d-none loading bg-light rounded-3 shadow-lg ">
		<span></span> <span></span> <span></span> <span></span> <span></span>

	</div>
	<img id="image-1" alt="" src="" />
	<div>

		<div id="boxcat" class="card shadow-sm"
			style="width: 100%; max-width: 300px; margin: auto;">
			<div class="card-body d-flex flex-column" style="height: 400px;">
				<div id="chatbox"
					class="border rounded-3 p-3 overflow-auto flex-grow-1"
					style="background-color: #f8f9fa; max-height: 250px;"></div>
				<div class="input-group mt-3 position-relative">
					<input type="text" id="message" class="form-control"
						placeholder="Nhập tin nhắn..." aria-label="Nhập tin nhắn">
					<button class="btn btn-primary" onclick="sendMessage()">Gửi</button>
					<div id="preview" class="position-absolute d-flex"
						style="top: -750%; left: -45%; min-height:"></div>
				</div>

			</div>
		</div>
		<footer class="bg-danger text-dark py-3">
			<div class="container text-center">
				<strong class="mb-0">&copy; 2024 Phan Huỳnh Phúc Khang -
					Java 4 - PS 42856</strong>
			</div>
		</footer>




		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
		<c:if test="${wrong}">
			<script>
                            var myModal = new bootstrap.Modal(document
                                .getElementById('loginModal'));
                            myModal.show();
                        </script>
		</c:if>
		<script type="text/javascript">
	if ('webkitSpeechRecognition' in window) {
	    const ghiAm = new webkitSpeechRecognition();
	    ghiAm.lang = 'vi-VN'; 
	    ghiAm.continuous = false; 
	    ghiAm.interimResults = false; 

	    const searchInput = document.getElementById('searchInput');
	    const voiceSearchButton = document.getElementById('voiceSearchButton');
	    const formSearch = document.getElementById('formSearchc');
	    const notiGhiam = document.getElementById('notiGM'); 
	    voiceSearchButton.addEventListener('click', function() {
	    	notiGhiam.classList.remove('d-none'); 
	    	ghiAm.start();
	    });

	    ghiAm.onresult = function(event) {
	        const ketQua = event.results[0][0].transcript; 

	        searchInput.value = ketQua; 
	        notiGhiam.classList.add('d-none'); 
	        formSearch.submit();
	    };
	    ghiAm.onerror = function(event) {
	        console.log('Error occurred: ' + event.error); 
	    };
	} else {
	    alert('Trình duyệt của bạn không hỗ trợ tính năng nhận diện giọng nói.');
	}
	
	const ctx = "<%=request.getAttribute("ctx")%>";	
	console.log('${uLog.id}');
	const url = "ws://localhost:8088"+ctx+'chat/'+'${uLog.id}';
	console.log(url);
  	socket = new WebSocket(url); 
	socket.onopen = function(event){
	 console.log("Kết nối thành công!");
 	}
	let idTo = null;
	let idFrom = null;
	let  mes = null;
	function createRoom(a,b){
		idTo =b;
		idFrom =a;
		
		console.log('taophong, id nhan:'+idTo)
		console.log('taophong, id gui:'+idFrom)
		const box = document.getElementById("boxcat");
		box.style.bottom = '-10%';
		
	}
	let checkImg = false;
	let file = null;
	let fileName = null;
	function sendMessage(){
		console.log("gửi tin");
		if (idFrom == null){
			console.log("from null")
			idFrom='${uLog.id}';
			console.log('${uLog.id}')
		}
		console.log('gui tin, id nhan:'+idTo)
		console.log('gui tin, id gui:'+idFrom)
		mes =document.getElementById("message").value;
		console.log('gui tin, tin gui:'+mes)
		 const message = {
				   name: null,
			       nguoiGui: idFrom,
			       nguoiNhan:idTo,
			       noiDung: mes,
			       image: null,
			       imageMes: fileName,
			    };
           if (message) {
        	   saveImage(file);
        	   socket.send(JSON.stringify(message));
        	   document.getElementById("message").value = "";
        	   document.getElementById("preview").innerHTML = "";
        	   console.log('gui tin, tin gui json:'+JSON.stringify(message))
           }
          
	}
	
	socket.onmessage = function (event) {
		console.log("giao dien chinh da doc dc:", event.data);
		const box = document.getElementById("boxcat");
		box.style.bottom = '-10%';
		console.log('nhantin nhan :'+ event.data);
		 let data = null;
		try {
		     data = JSON.parse(event.data);
		    console.log("da doc dc:", data);
		} catch (error) {
		    console.error("Lỗi khi parse JSON:", error);
		}
	    
	    const chatbox = document.getElementById("chatbox");
	    if (checkImg) {
	    	 const messageElement = document.createElement("p");
		        messageElement.innerHTML = 
		            '<div class="bg-light border rounded p-2 mb-2 shadow-sm" style="max-width: 80%; word-wrap: break-word;">' +
		                '<div class="row align-items-center">' +
		                    '<div class="col-4 p-0 d-flex justify-content-center align-items-center">' +
		                        '<img class="w-50 rounded-5" alt="" src="'+ctx+'imgs/'+data.image+'"/>' +
		                    '</div>' +
		                    '<div class="col-8 fw-bold text-dark">' +
		                        data.name +
		                    '</div>' +
		                '</div>' +
		                '<div class="row">'+
						    '<img src="'+ctx+'imgs/'+data.imageMes+'" class="w-100 img-thumbnail" alt="">'+
						'</div>'+
		                '<p class="mb-0 text-dark">' + data.noiDung + '</p>' +
		            '</div>';

		        chatbox.appendChild(messageElement);
	    } else {
	        const messageElement = document.createElement("p");
	        messageElement.innerHTML = 
	            '<div class="bg-light border rounded p-2 mb-2 shadow-sm" style="max-width: 80%; word-wrap: break-word;">' +
	                '<div class="row align-items-center">' +
	                    '<div class="col-4 p-0 d-flex justify-content-center align-items-center">' +
	                        '<img class="w-50 rounded-5" alt="" src="'+ctx+'imgs/'+data.image+'"/>' +
	                    '</div>' +
	                    '<div class="col-8 fw-bold text-dark">' +
	                        data.name +
	                    '</div>' +
	                '</div>' +
	                '<p class="mb-0 text-dark">' + data.noiDung + '</p>' +
	            '</div>';

	        chatbox.appendChild(messageElement);
	    }
	    if (data?.nguoiNhan?.trim() == `${uLog.id}`) {
	        idTo = data.nguoiGui?.trim() ? data.nguoiGui.trim() : null;
	        idFrom = data.nguoiNhan?.trim() ? data.nguoiNhan.trim() : null;
	    } else if (data?.nguoiGui?.trim() == `${uLog.id}`) {
	        idTo = data.nguoiNhan?.trim() ? data.nguoiNhan.trim() : null;
	        idFrom = data.nguoiGui?.trim() ? data.nguoiGui.trim() : null;
	    }

	    console.log('Gửi:', idFrom);
	    console.log('Nhận:', idTo);
	    chatbox.scrollTop = chatbox.scrollHeight;

	};
	
	
	const messageInput = document.getElementById('message');
    const preview = document.getElementById('preview');
    messageInput.addEventListener('paste', (event) => {
        const clipboardItems = event.clipboardData.items;
        
        for (let item of clipboardItems) {
          if (item.type.startsWith('image/')) {
        	fileName = Math.floor(Math.random() * 10000000000) + '.' + item.type.split('/')[1];
            file = new File([ item.getAsFile()], fileName, { type:  item.type });
            console.log('Tên ảnh:', file.name);
            const reader = new FileReader();

            reader.onload = (e) => {
              const img = document.createElement('img');
              img.src = e.target.result;
              img.style.maxWidth = '100px';
              img.style.marginRight = '10px';
              img.style.border = '1px solid #ddd';
              img.style.borderRadius = '5px';
              preview.appendChild(img);
              checkImg= true;
            };

            reader.readAsDataURL(file);
            break;
          }
        }
      });



    messageInput.addEventListener('dragover', (event) => {
        event.preventDefault();
      });
      messageInput.addEventListener('drop', (event) => {
        event.preventDefault();
        const files = event.dataTransfer.files;
        for (let item of files) {
          if (item.type.startsWith('image/')) { 
              file = item;
              fileName =item.name;
            const reader = new FileReader();
            reader.onload = (e) => {
              const img = document.createElement('img');
              img.src = e.target.result;
              img.style.maxWidth = '100px';
              img.style.marginRight = '10px';
              img.style.border = '1px solid #ddd';
              img.style.borderRadius = '5px';
              preview.appendChild(img); 
            };

            reader.readAsDataURL(item);
          }
        }
      });

    function saveImage(file) {
    	  const formData = new FormData();
    	  formData.append('image', file);

    	  fetch(ctx+'upload-image', {
    	    method: 'POST',
    	    body: formData,
    	  })    	  
    	  .catch(error => {
    	    console.error(error);
    	  });
    	}
	</script>
</body>

</html>