<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm"%>
<h4 class="mb-4">Video gợi ý</h4>
<div class="row ">
	<c:forEach var="video" items="${listVD}">
		<div class="col-4 mb-5">
			<a href="${ctx}index/video/chitiet/${video.id}"
				class="text-decoration-none h-100"> <img src="${video.poster}"
				class="rounded-3 w-100">
				<div class="row p-2 mt-2">
					<div class="col-1 p-0 m-0 ">
						<img alt="" class="w-100 rounded-5" src="${ctx}imgs/${video.author.image}"/>
					</div>
					<div class="col-11">
						<h6 class=" text-dark">
							<c:choose>
								<c:when test="${fn:length(video.title) > 40}">
	                                ${fn:substring(video.title, 0, 50)}...
	                            </c:when>
								<c:otherwise>
	                                    ${video.title}
	                            </c:otherwise>
							</c:choose>
						</h6>
						<p  class=" text-muted text-decoration-none m-0">${video.author.fullname} <i class="fa-solid fa-circle-check"></i></p>
						<p class="m-0">
							<small class="text-muted">${video.views} <i
								class="fa-solid fa-eye"></i> - 
								<fm:formatDate value="${video.dateUpload}" pattern="dd-MM-yyyy" />
							</small>
						</p>
					</div>
				</div>
			</a>
		</div>
	</c:forEach>

</div>