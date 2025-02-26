<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm"%>
<c:url value="/" var="ctx" />
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>YouTube Fake</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<div class="row col-11 mx-auto ">
	<div class="col-8">
		<div class="ratio ratio-16x9 row">
		
			<iframe class="w-100" src="${tempVd.link}"
				title="YouTube video player" frameborder="0"
				allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
				referrerpolicy="strict-origin-when-cross-origin" allowfullscreen>
			</iframe>
			
		</div>
		<div class="row my-2">
			<h4>${tempVd.title}</h4>
		</div>
		<div class="row">
			<div class="col-1">
				<img alt="" class="w-100 rounded-5"
					src="${ctx}imgs/${tempVd.author.image}" />
			</div>
			<div class="col-2 p-0">
				<a href="${ctx}/index/video/user/${tempVd.author.id}" class=" text-muted text-decoration-none  fw-bold m-0">
					${tempVd.author.fullname} <i class="fa-solid fa-circle-check"></i>
				</a>
				<div class=" text-secondary">${fn:length(tempVd.author.subscribers)} subscribers</div>
			</div>
			<c:choose>
				<c:when test="${subscribed}">
					<form action="${ctx}index/userview/chitiet/unSub" method="post"  class="col-2 my-auto">
						<input value="${tempVd.author.id}" name="userAuth" style="display: none;" type="text"/>
						<input value="${sessionScope.idLogin}" name="userDk" style="display: none;" type="text"/>
						<input type="text" name="videoId" value="${tempVd.id}" class="d-none" />
						<button class="btn fw-bold btn-outline-danger rounded-5">UnSubscribe</button>
					</form>
				</c:when>
				<c:when test="${tempVd.author.id == sessionScope.idLogin}">
					<form action="${ctx}index/video/editVideo/${tempVd.id}" method="post"  class="col-2 my-auto">
						<input value="${tempVd.author.id}" name="userAuth" style="display: none;" type="text"/>
						<input value="${sessionScope.idLogin}" name="userDk" style="display: none;" type="text"/>
						<input type="text" name="videoId" value="${tempVd.id}" class="d-none" />
						<button class="btn fw-semibold btn-primary rounded-5">Chỉnh sửa</button>
					</form>
				</c:when>
				<c:otherwise>
					<form action="${ctx}index/userview/chitiet/insertSub" method="post"  class="col-2 my-auto">
						<input value="${tempVd.author.id}" name="userAuth" style="display: none;" type="text"/>
						<input value="${sessionScope.idLogin}" name="userDk" style="display: none;" type="text"/>
						<input type="text" name="videoId" value="${tempVd.id}" class="d-none" />
						<button class="btn fw-bold btn-dark rounded-5">Subscribe</button>
					</form>
				</c:otherwise>
			</c:choose>
			
			
			<div class="col-1 p-0 position-relative d-flex justify-content-center align-items-center">
				<div class="cube-loader ">
				  <div  class="cube-top d-flex justify-content-center fs-3 text-light fw-semibold align-items-center">
				  	${viewCount}
				  </div>
				  <div class="cube-wrapper">
				    <span style="--i:0" class="cube-span d-flex justify-content-center fs-4 align-items-center">
				    	<p class="m-0 p-0 text-light"><i class="text-light fa-solid fa-eye"></i></p>
				    </span>
				    <span style="--i:1" class="cube-span   d-flex justify-content-center fs-6 align-items-center  fw-semibold">
				    	<p class="m-0 p-0 text-light">LIVE</p>
				    </span>
				    <span style="--i:2" class="cube-span  d-flex justify-content-center fs-6 align-items-center  fw-semibold">
				    	<small class="m-0 p-0 text-light">VIEWS</small>
				    	</span>
				    <span style="--i:3" class="cube-span d-flex justify-content-center fs-6 align-items-center text-light fw-semibold">LIVE</span>
				  </div>
				</div>
			</div>
			<div class="col-3 btn d-flex">
				<div class="input-group w-100">
					<c:choose>
						<c:when test="${empty sessionScope.idLogin}">
							<a data-bs-toggle="modal" data-bs-target="#loginModal"
								class="text-decoration-none ps-3 w-25  text-black btn bg-secondary-subtle rounded-start-5">
								<i class="fa-regular fa-thumbs-up"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a
								href="${ctx}index/userview/chitiet/${Liked ? 'disLike':'like'}/${tempVd.id}"
								class=" text-decoration-none ps-3 w-25  ${Liked ? 'text-light btn bg-black': 'text-black btn bg-secondary-subtle'} rounded-start-5">
								<i class="fa-regular fa-thumbs-up"></i>
							</a>
						</c:otherwise>
					</c:choose>
					<select readonly
						class="bg-secondary-subtle rounded-end-5 form-select w-75"
						aria-label="Likes Select">
						<option>${fn:length(listOfF)}lượt thích</option>
						<c:forEach varStatus="i" var="name" items="${listOfF}">
							<option>${i.count}${name.user.fullname}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="col-2 my-auto">
				<button class="btn col-12 fw-semibold bg-secondary-subtle rounded-5">
					<i class="fa-solid fa-share"></i> Share
				</button>
			</div>
			<div class="col-1 my-auto">
				<button
					class="btn fw-semibold float-end  bg-secondary-subtle rounded-5">
					<i class="fa-solid fa-ellipsis"></i>
				</button>
			</div>
		</div>
		<div class="row rounded-3 bg-secondary-subtle p-3 my-2 me-1">
			<p class="fw-semibold">${tempVd.views}views${tempVd.dateUpload}</p>
			<p>${tempVd.description}</p>
		</div>
		<div class="row mt-2">
			<h1 class="fs-4">${fn:length(tempVd.comments)} Comments</h1>
		</div>
		<div class="row pe-3">
			<c:if test="${!cmtEd}">
				<div class="col-1 my-auto">
					<img alt="" class="w-100 rounded-5"
						src="${ctx}imgs/${empty sessionScope.idLogin ? 'emptyU.jpg':uLog.image}" />
				</div>
				<form action="${ctx}index/userview/chitiet/comment" method="post"
					class="col-11">
					<div class="row mb-2 me-2">
						<input name="cmt" placeholder="Add a comment..." type="text"
							class="inputv" required="required">
					</div>
					<input type="text" name="idVideo" value="${tempVd.id}"
						class="d-none" />
					<div class="row">
						<div class="col text-end">
							<c:choose>
								<c:when test="${empty sessionScope.idLogin}">
									<a data-bs-toggle="modal" data-bs-target="#loginModal"
										class="btn  rounded-5 btn-outline-dark">Comment</a>
								</c:when>
								<c:otherwise>
									<button class="btn rounded-5 btn-outline-dark">Comment</button>
								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</form>
			</c:if>
			<div class="row">
				<c:forEach items="${ListCmt}" varStatus="c" var="lCmt">
					<div class="row my-2 ${c.first && cmtEd ? 'bg-secondary-subtle text-black pt-2 py-2 rounded-3':''}">
						<div class="col-1">
							<img alt="" class="w-100 rounded-5"
								src="${ctx}imgs/${lCmt.user.image}" />
						</div>
						<div class="col-10">
							<div class="row ">
							<a href="${ctx}/index/video/user/${lCmt.user.id}" class=" text-muted text-decoration-none ">
								<strong>${lCmt.user.fullname} <span class="fw-lighter">
										<small class="text-muted ms-2"><fm:formatDate value="${lCmt.dateComment}" pattern="dd-MM-yyyy" /></small>
								</span>
								</strong></a>
							</div>
							<c:choose>
								<c:when test="${c.first && edit}">
									<form action="${ctx}index/userview/chitiet/cmt/update" method="post" >
										<div class="row mb-2 me-2">
											<input name="cmt" value="${lCmt.content}" type="text" class="inputv" required="required">
											<input class="d-none" name="cmtId" value="${lCmt.id}" type="text"/>
											<input class="d-none" name="videoId" value="${tempVd.id}" type="text"/>
										</div>
										<div class="row">
											<div class="col text-end">
												<button class="btn rounded-5 btn-outline-dark">Chỉnh sửa</button>
											</div>
										</div>
									</form>
								</c:when>
								<c:otherwise>
									<div class="row">
										<p>${lCmt.content}</p>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-1">
						    <div class="dropdown">
						        <i class="fa-solid fa-ellipsis-vertical  ${ c.first && cmtEd ?'cursor-pointer':''}" data-bs-toggle="${ c.first && cmtEd ?'dropdown':''}"></i>
						        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						            <li>
						                <a class="dropdown-item text-dark" href="${ctx}index/userview/chitiet/cmt/edit?videoId=${tempVd.id}">
						                    <i class="fa-solid fa-edit me-2"></i> Sửa
						                </a>
						            </li>
						            <li>
						                <a class="dropdown-item text-danger" href="${ctx}index/userview/chitiet/cmt/delete?videoId=${tempVd.id}&cmtId=${lCmt.id}">
						                    <i class="fa-solid fa-trash me-2"></i> Xóa
						                </a>
						            </li>
						        </ul>
						    </div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>


	</div>
	<div class="col-4 ">
		<c:forEach var="y" items="${ListOut}">
			<a class="text-decoration-none text-black"
				href="${ctx}index/video/chitiet/${y.id}">
				<div class="row mb-2">
					<div class="col-5 p-0 ">
						<img alt="" class=" rounded-3 w-100" src="${y.poster}" />
					</div>
					<div class="col-6">
						<h6>
							<c:choose>
								<c:when test="${fn:length(y.title) > 28}">
								${fn:substring(y.title, 0, 28)}...
							</c:when>
								<c:otherwise>
							 	${y.title}
							</c:otherwise>
							</c:choose>
						</h6>
						
						<small class="text-muted">
						
							${y.author.fullname} <i
							class="fa-solid fa-circle-check"></i></small> <br> <small
							class="text-muted">${y.views} views <fm:formatDate value="${y.dateUpload}" pattern="dd-MM-yyyy" /></small>
					</div>
					<div class="col-1 p-0 m-0">
						<i class="fa-solid fa-ellipsis-vertical"></i>
					</div>
				</div>
			</a>
		</c:forEach>
	</div>
</div>