<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main class="flex-grow-1">
<div class="container mt-4">
  <div class="row">
    <c:forEach var="item" items="${itemList}">
      <div class="col-md-3 mb-4">
        <div class="card h-100">
          <c:choose>
            <c:when test="${empty item.imageUrl}">
                <img src="" class="card-img-top" alt="기본 이미지">
            </c:when>
            <c:otherwise>
                <a href="/product/productDetail?prodNo=${item.prodNo}">
                    <img src="${item.imageUrl}" class="card-img-top" alt="상품 이미지">
                </a>
            </c:otherwise>
          </c:choose>
          <div class="card-body">
            <h5 class="card-title">
                <a href="/product/productDetail?prodNo=${item.prodNo}" style="text-decoration: none;">
            ${item.prodName}
            </a>
            </h5>
            <p class="card-text">${item.price}원</p>
            <button class="btn btn-primary add-to-cart-btn" data-prodno="${item.prodNo}">장바구니 담기</button>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
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
                    window.location.href="/cart/cartlist";
                } else {

                }
             } else {
                alert(data.message);
             }
          })
          .catch(err => {
             alert("에러 발생 : " + err.message);
          });
        });
      });
    });

</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>