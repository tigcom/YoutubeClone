<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm"%>
<!DOCTYPE html>


<div class="row">
	<div class="col-1 ">
		<h1 class="text-center py-2 rounded-circle  bg-danger">
			<i class="text-light fa-solid fa-fire"></i>
		</h1>
	</div>
	<div class="col-11 d-flex align-items-center">
		<h2 class="fw-bold ">Trending</h2>
	</div>
</div>
<div class="row mt-2">
	<p class="fw-semibold text-dark-emphasis">Top video theo lượt thích</p>
	<hr>
</div>
<div class="row">
	<c:forEach var="y" items="${ListSort}">
		<a class="text-decoration-none text-black"
			href="${ctx}index/video/chitiet/${y.id}">
			<div class="row mb-2">
				<div class="col-1 d-flex justify-content-center align-items-center">
    				<h3 class="m-0">${fn:length(y.favorites)} <i class="fa-solid fa-thumbs-up fs-6 ms-1"></i> </h3>
				</div>
				<div class="col-4 p-0 ">
					<img alt="" class=" rounded-3 w-100" src="${y.poster}" />
				</div>
				<div class="col-6">
					<h6>${y.title}</h6>
					<div class="row">
						<small class="text-muted">${y.author.fullname} <i class="fa-solid fa-circle-check"></i>
						 	${y.views} views
							${y.dateUpload}</small><br>
						<small class="text-muted">${y.description}</small>
					</div>
				</div>
				<div class="col-1">
					<i class="fa-solid fa-ellipsis-vertical"></i>
				</div>
			</div>
		</a>
	</c:forEach>
</div>