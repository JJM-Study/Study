<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main class="flex-grow-1">
    <div class="container mt-4">
        <div class="row">
            <c:forEach var="item" items="${itemList.imageList}">
              <div class="col-md-3 mb-3">
                <div class="card">
                    <img src="${item.imageUrl}"
                         alt="${item.isMain == 'Y' ? '메인 상품 이미지' : '상품 이미지' }"
                         class="card-img-top" style="height: 300px; object-fit: contain;"/>
                </div>
              </div>
            </c:forEach>
        </div>

      <p>가격 : "${itemList.price}"원</p>
      <div class="product-detail-desc">
         <c:out value="${itemList.detailDesc}" escapeXml="false" />
      </div>
      <button class="btn btn-primary add-to-cart-btn" data-prodno="${itemList.prodNo}">장바구니 담기</button>
    </div>
</main>


<script src="/js/cart.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.add-to-cart-btn').forEach(function (btn) {
            btn.addEventListener('click', function() {
                const prodNo = this.dataset.prodno;

                addToCart(prodNo).then(data => {
                    if (data.success) {
                       if (confirm("담기 성공했습니다. 장바구니로 이동하시겠습니까?")) {
                            window.location.href="cart/cartlist";
                       } else {

                       }

                    } else {
                        alert(data.message);
                    }
                }).catch(err => {
                    alert("에러 발생 : " + err.message);
                });
            });
        });
    });

</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>