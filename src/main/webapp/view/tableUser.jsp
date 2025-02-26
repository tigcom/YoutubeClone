<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm"%>
<!DOCTYPE html>

<div class="row">
	<div class="col-1 ">
		<h1 class="text-center py-2 rounded-circle  bg-danger">
			<a class="text-light" href="${ctx}index/video/tableUser">
				<i class="fa-solid fa-users"></i>
			</a>
		</h1>
	</div>
	<div class="col-11 d-flex align-items-center">
		<h2 class="fw-bold me-3">Quản lý người dùng</h2>
		<a href="${ctx}admin/newUser" class="btn btn-outline-danger">Thêm mới</a>
	</div>
	
</div>
<hr>
<form id="formSearch" action="${ctx}index/video/searchUser" method="post">

	<label id="idS" for="search">Search</label> 
	<input required="" pattern=".*\S.*" type="search" name ="searchU" class="input" id="search"> 
	<span class="caret"></span>
</form>
<hr>
<h3>
	${key}
</h3>
<table class="table align-middle mb-0 bg-white">
	<thead class="bg-light">
		<tr class="">
			<th>No.</th>
			<th>Người dùng</th>
			<th class="text-center">Email</th>
			<th class="text-center">Giới tính</th>
			<th class="text-center">Số điện thoại</th>
			<th class="text-center">Vai trò</th>
			<th class="text-center">Thao tác</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" varStatus="u" items="${listUser}">
			<tr>
				<td>${u.count}</td>
				<td>
					<div class="  d-flex align-items-center">
						<img src="${ctx}imgs/${user.image}" alt=""
							style="width: 45px; height: 45px" class="rounded-circle" />
						<div class="ms-3">
							<p class="fw-bold mb-1">${user.id}</p>
							<a href="${ctx}/index/video/user/${user.id}" class=" text-muted text-decoration-none ">
							<p class="text-muted mb-0">${user.fullname}</p></a>
						</div>
					</div>
				</td>
				<td class="text-center">
					<p class="fw-normal mb-1">${user.email}</p>
				</td>
				<td class="text-center"><span
					class="badge ${user.gender ? 'bg-dark' : 'bg-danger'}">${user.gender ? 'Nam' : 'Nữ'}</span>

				</td class="text-center">
				<td class="text-center">${user.phone}</td>
				<td class="text-center"><span
					class="badge ${user.admin ? 'bg-primary' :'text-dark'}">
						${user.admin ? 'Quản trị viên' :'Người dùng'} </span></td>
				<td class="text-center">
					<a href="${ctx}admin/editUser/${user.id}" type="button" class="btn btn-outline-danger">Sửa</a>
					<a href="${ctx}admin/delUser/${user.id}" type="button" class="btn btn-outline-dark">Xóa</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>