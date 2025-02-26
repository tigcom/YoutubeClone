<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm"%>
<!DOCTYPE html>


<div class="row">
	<div class="col-1 ">
		<h1 class="text-center py-2 rounded-circle  bg-danger">
			<c:choose>
				<c:when test="${checkNO}">
					<i class="fas fa-bolt"></i>
				</c:when>
				<c:otherwise>
				 	<i class="fas fa-history"></i>
				</c:otherwise>
			</c:choose>
		</h1>
	</div>
	<div class="col-11 d-flex align-items-center">
		<h2 class="fw-bold ">${checkNO ? 'Ngày đăng từ mới đến cũ':'Ngày đăng từ cũ đến mới'}</h2>
	</div>
</div><hr>
<div class="row p-2">
	<c:forEach var="y" items="${listVD}">
		<a class="text-decoration-none text-black"
			href="${ctx}index/video/chitiet/${y.id}">
			<div class="row my-2 p-2">
				<div class="col-4 p-0 ">
					<img alt="" class=" rounded-3 w-100" src="${y.poster}" />
				</div>
				<div class="col-7">
					<h6>${y.title}</h6>
					<p class=" text-muted m-0">${y.author.fullname} <i class="fa-solid fa-circle-check"></i></p>
					<small class="text-muted">${y.views} views</small> 
						<br>
						<span class=" badge bg-primary">
							Ngày đăng: ${y.dateUpload}
						</span>
					<small class="text-muted">
					<c:choose>
						<c:when test="${fn:length(y.description) > 720}">
							${fn:substring(y.description, 0, 710)}...
						</c:when>
						<c:otherwise>
						 	${y.description}
						</c:otherwise>
					</c:choose>
					</small>
				</div>
				<div class="col-1">
					<i class="fa-solid fa-ellipsis-vertical"></i>
				</div>
			</div>
		</a>
	</c:forEach>
</div>