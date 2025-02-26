<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kênh của ${channel.user.fullName}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet"  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <div class="container mt-1">
        <div class="row mb-4 align-items-center">
            <div class="col-2">
                <img src="${ctx}imgs/${chanel.image}" alt="Avatar" class="rounded-circle w-100">
            </div>
            <div class="col-8">
                <h2>${chanel.fullname}</h2>
                <p class="my-1">${chanel.id} <strong>·</strong> <span class="text-muted">${fn:length(chanel.videos)} video</span></p> 
                <p  class="my-1"  data-bs-toggle="modal" data-bs-target="#exampleModal"><span class="text-muted my-1">${fn:length(chanel.subscribers)} người đăng ký</span></p>
                <p  class="my-1"  data-bs-toggle="modal" data-bs-target="#exampleModal2"><span class="text-muted my-1">Đang theo dõi ${fn:length(chanel.subscribList)}  người</span></p>
                <div class="row my-2">
                	<c:choose>
                		<c:when test="${sessionScope.idLogin == chanel.id}">
                			<a href="${ctx}userview/editUser/${chanel.id}" class="btn m-1 bg-secondary-subtle col-3  rounded-5"><small class="fw-semibold">Tùy chỉnh kênh</small></a>
                		</c:when>
                		
                	</c:choose>
                    
                    <c:if test="${chanel.id == uLog.id}">
                    	<a href="${ctx}admin/createVideo" class="btn m-1 bg-secondary-subtle col-2 rounded-5"><small class="fw-semibold">Thêm video</small></a>
                    </c:if>
                </div>
            </div>
            
        </div>
        <h6 class="border-bottom border-2 border-black my-0 col-2 text-center py-3">Danh sách video tải lên</h6 >

        <hr class="my-1">
        <h4 class="my-4">Video</h4>
        <div class="row">
        <c:choose>
        	<c:when test="${chanel.id == uLog.id}">
        		<c:forEach var="vchanel" items="${chanel.videos}">
	        		
	        		<div class="col-md-3 m-3 bg-o position-relative">
	                <a class="row p-0" href="${ctx}index/video/chitiet/${vchanel.id}">
	                    <img  src="${vchanel.poster}" class="rounded-3 w-100 p-0  ${vchanel.active ? '':'opacity-25'} " alt="">
	                </a>
	                <div class="row">
	                    <div class="col-10 my-1 flex-grow-1">
	                        <h6 class="card-title my-1  ${vchanel.active ? '':'opacity-25'}">
	                        <c:choose>
	                        	<c:when test="${fn:length(vchanel.title)>35}">
	                        		 ${fn:substring(vchanel.title, 0, 35)}...
	                        	</c:when>
	                        	<c:otherwise>
	                        		${vchanel.title}
	                        	</c:otherwise>
	                        </c:choose>
	                        </h6>
	                            <p class="card-text text-muted">
	                                ${vchanel.dateUpload} <strong>·</strong> ${vchanel.views} lượt xem
	                            </p>
	                    </div>
	                    <div class="col-1">
						    <div class="dropdown bg-dr" style="cursor: pointer;">
						        <i id="dropdownMenuButton"  class="fa-solid  fa-ellipsis-vertical  " data-bs-toggle="dropdown" aria-expanded="false"></i>
						        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						            <li>
						                <a class="dropdown-item text-dark" href="${ctx}admin/editVideo?videoId=${vchanel.id}">
						                    <i class="fa-solid fa-edit me-2"></i> Sửa
						                </a>
						            </li>
						            <li>
						                <a class="dropdown-item text-danger" href="${ctx}admin/deleteVideo?videoId=${vchanel.id}">
						                    <i class="fa-solid fa-trash me-2"></i> Xóa
						                </a>
						            </li>
						            <li>
						                <a class="dropdown-item text-danger" href="${ctx}admin/${vchanel.active ? 'hideVideo':'showVideo'}?videoId=${vchanel.id}">
						                    <i class="fa-regular fa-eye-slash me-2"></i> ${vchanel.active ? 'Ẩn':'Hiện'}
						                </a>
						            </li>
						        </ul>
						    </div>
						</div>
	                </div>
	                	<c:if test="${!vchanel.active}">
	                		<h4 class="fs-1 position-absolute top-50 end-50"><i class="fa-solid fa-eye-low-vision"></i></h4>
	                	</c:if>
	                </div>
	        	</c:forEach>
        	</c:when>
        	<c:otherwise>
        		<c:forEach var="vchanel" items="${chanel.videos}">
	        		<c:if test="${vchanel.active}">
	        			<div class="col-md-3 m-3 bg-o position-relative">
	                <a class="row p-0" href="${ctx}index/video/chitiet/${vchanel.id}">
	                    <img  src="${vchanel.poster}" class="rounded-3 w-100 p-0  " alt="">
	                </a>
	                <div class="row">
	                    <div class="col-10 my-1 flex-grow-1">
	                        <h6 class="card-title my-1 ">
	                        <c:choose>
	                        	<c:when test="${fn:length(vchanel.title)>35}">
	                        		 ${fn:substring(vchanel.title, 0, 35)}...
	                        	</c:when>
	                        	<c:otherwise>
	                        		${vchanel.title}
	                        	</c:otherwise>
	                        </c:choose>
	                        </h6>
	                            <p class="card-text text-muted">
	                                ${vchanel.dateUpload} <strong>·</strong> ${vchanel.views} lượt xem
	                            </p>
	                    </div>
	                    
	                </div>
	                </div>
	        		</c:if>
	        	</c:forEach>
        	</c:otherwise>
        </c:choose>
        	
                
            </div>
            
        </div>
    

    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Danh sách người đăng ký</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <c:forEach items="${chanel.subscribers }" var="suber">
        	<div class="row my-2">
        		<div class="col-1">
					<img id="image-1" class="w-100 rounded-5" alt="" src="${ctx}/imgs/${suber.userDk.image}"/>
        		</div>
        		<div class="col-7">
					<strong>${suber.userDk.fullname}</strong>
        		</div>
        		<div class="col-2 text-end">
					<button onclick="createRoom('${suber.userAuth.id}','${suber.userDk.id}')"><i class="fa-solid fa-comment"></i></button>
        		</div>
        	</div>
        	<hr>
        </c:forEach>
      </div>
      
    </div>
  </div>
</div>
 <div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Danh sách người đăng ký</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <c:forEach items="${chanel.subscribList }" var="suber">
        	<div class="row my-2">
        		<div class="col-1">
					<img id="image-1" class="w-100 rounded-5" alt="" src="${ctx}/imgs/${suber.userDk.image}"/>
        		</div>
        		<div class="col-7">
					<strong>${suber.userAuth.fullname}</strong>
        		</div>
        		<div class="col-2 text-end">
					<button onclick="createRoom('${suber.userDk.id}','${suber.userAuth.id}')"><i class="fa-solid fa-comment"></i></button>
        		</div>
        	</div>
        	<hr>
        </c:forEach>
      </div>
      
    </div>
  </div>
</div>
	<c:if test="${checkDel}">
		<script type="text/javascript">
		alert("Xóa thất bại !!, dữ liệu video đang được sử dụng, hãy thử ẩn video.");
		
   		</script>
	</c:if>


    
</body>
</html>
