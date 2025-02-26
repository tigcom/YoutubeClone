<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<c:forEach var="y" items="${listVD}">
    <a class="text-decoration-none text-black" href="${ctx}index/video/chitiet/${y.id}">
        <div class="row mb-4">
            <div class="col-4 p-0">
                <img alt="" class="rounded-3 w-100" src="${y.poster}" />
            </div>
            <div class="col-7">
                <h6 class="fw-bold">${y.title}</h6>
                <table class="table table-bordered table-striped table-hover shadow-sm rounded-3">
                    <thead class="table-dark">
                        <tr>
                            <th>Người thích</th>
                            <th>Ngày thích</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ytemp" items="${y.favorites}">
                            <tr>
                                <td>${ytemp.user.fullname}</td>
                                <td>${ytemp.likeDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-1 d-flex justify-content-center align-items-center">
                <i class="fa-solid fa-ellipsis-vertical"></i>
            </div>
        </div>
    </a>
</c:forEach>

</div>