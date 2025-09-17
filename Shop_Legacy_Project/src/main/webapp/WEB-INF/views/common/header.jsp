<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${pageTitle}" default="ShopLegacy" /></title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="/css/common.css">
</head>
<body>
<div class="wrapper d-flex flex-column min-vh-100">

<c:if test="${showNav !=false}">
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
          <div class="container-fluid">
            <a class="navbar-brand" href="/">ShopLegacy</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarNav" aria-controls="navbarNav"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="/">홈</a>
            </li>
             <li class="nav-item">
                <a class="nav-link" href="/cart/cartlist">장바구니</a>
             </li>
             <li class="nav-item">
                <a class="nav-link" href="/product/productList">상품</a>
             </li>
             <li class="nav-item">
                <a class="nav-link" href="/">메뉴</a>
             </li>
            </ul>
            </div>
          </div>
        </nav>
    </header>
    </c:if>
