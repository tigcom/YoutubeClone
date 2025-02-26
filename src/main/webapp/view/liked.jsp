<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
-->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm"%>
<!DOCTYPE html>


<div class="row">
	<div class="col-1 ">
		<h1 class="text-center py-2 rounded-circle  bg-danger">
			<i class="text-light fas fa-play me-2"></i>
		</h1>
	</div>
	<div class="col-11 d-flex align-items-center">
		<h2 class="fw-bold ">Video đã thích</h2>
	</div>
</div>
<div class="row mt-2">
	<p class="fw-semibold text-dark-emphasis">Danh sách video đã thích</p>
	<hr>
</div>
<div class="row">
	<c:forEach var="y" items="${ListLiked}">
		<a class="text-decoration-none text-black"
			href="${ctx}index/video/chitiet/${y.video.id}">
			<div class="row mb-2">
				<div class="col-4 p-0 ">
					<img alt="" class=" rounded-3 w-100" src="${y.video.poster}" />
				</div>
				<div class="col-7">
					<h6>${y.video.title}</h6>
					
					<small class="text-muted">${y.video.author.fullname} <i class="fa-solid fa-circle-check"></i></small> 
					<small class="text-muted">${y.video.views} views
						${y.video.dateUpload}</small>  <br>
						<span class=" badge bg-primary">
							Ngày thích: ${y.likeDate}
						</span>
						<br>
					<small class="text-muted">
					<c:choose>
						<c:when test="${fn:length(y.video.description) > 720}">
							${fn:substring(y.video.description, 0, 710)}...
						</c:when>
						<c:otherwise>
						 	${y.video.description}
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