<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     	<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fm" %>
<!DOCTYPE html>
 <div class="container mt-5">
        <h2>Danh sách Checklog</h2>
        <form action="${ctx}admin/checklogView" method="post" class="mb-4">
            <div class="row">
                <div class="col">
                    <label  class="form-label">Ngày bắt đầu</label>
                    <input type="date"  class="form-control"  name="endDate" >
                </div>
                <div class="col">
                    <label class="form-label">Ngày kết thúc</label>
                    <input type="date"  name="startDate" class="form-control">
                </div>
                <div class="col d-flex align-items-end">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                </div>
            </div>
        </form>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Thời gian truy cập</th>
                    <th>Tên người dùng</th>
                    <th>Thiết bị</th>
                    <th>URI</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="checklog" items="${checklogs}">
                    <tr>
                        <td>${checklog.id}</td>
                        <td>${checklog.accecsTime}</td>
                        <td>${checklog.userName}</td>
                        <td>${checklog.device}</td>
                        <td>${checklog.URI}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.0/js/bootstrap.min.js"></script>