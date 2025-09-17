<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main class="flex-grow-1">
<div class="container mt-4">
<%-- 현재 정렬 상태를 유지하려고 hidden input 사용 --%>
<form id="sortForm" method="get" action="/cart/cartlist">
    <input type="hidden" name="orderColumn" id="orderColumn">
    <input type="hidden" name="orderType" id="orderType">
</form>


<!-- 기존 cartList 출력 부분 -->
<div class="container-fluid">
<table class="w-100 table table-bordered list-table">
    <thead>
        <tr>
            <th><input type="checkbox" id="selectAll" onClick="toggleAll(this)"></th>
            <th>
                <a href="javascript:void(0);" onClick="toggleOrderType('cart_no')">
                    카트번호
                    <c:if test="${orderColumn == 'cart_no'}">
                        <c:choose>
                            <c:when test="${orderType == 'asc'}">▲</c:when>
                            <c:otherwise>▼</c:otherwise>
                        </c:choose>
                    </c:if>
                </a>
            </th>
            <th>
                <a href="javascript:void(0);" onClick="toggleOrderType('prod_name')">
                    제품명
                    <c:if test="${orderColumn == 'prod_name'}">
                        <c:choose>
                            <c:when test="${orderType == 'asc'}">▲</c:when>
                            <c:otherwise>▼</c:otherwise>
                        </c:choose>
                    </c:if>
                </a>
            </th>
            <th>
                <a href="javascript:void(0);" onClick="toggleOrderType('qty')">
                    수량
                    <c:if test="${orderColumn == 'qty'}">
                        <c:choose>
                            <c:when test="${orderType == 'asc'}">▲</c:when>
                            <c:otherwise>▼</c:otherwise>
                        </c:choose>
                    </c:if>
                </a>
            </th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${not empty cartList}">
                <c:forEach var="cart" items="${cartList}">
                    <tr>
                        <td><input type="checkbox" name="cartItem" value="${cart.cartNo}"></td>
                        <td>${cart.cartNo}</td>
                        <td>${cart.prodName}</td>
                        <td>${cart.qty}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4" class="text-center">비어있습니다</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

    <input type="button" onClick="submitOrder()" id="btn_orderSubmit" value="주문하기"></input>

    </div>
</div>
</main>

<script>
    let currentOrderColumn = "${orderColumn}";
    let currentOrderType = "${orderType}";

    function toggleOrderType(column) {
        let newOrderType ="asc";
        if (currentOrderColumn === column && currentOrderType === "asc") {
            newOrderType = "desc";
        }

        document.getElementById("orderColumn").value = column;
        document.getElementById("orderType").value = newOrderType;

        document.getElementById("sortForm").submit();
    }

    function toggleAll(source) {
        const checkboxes = document.getElementsByName('cartItem');
        for (let i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = source.checked;
        }
    }

    function submitOrder() {
        const selectedItems = [];
        const checkboxes = document.getElementsByName('cartItem');

        for (let i = 0; i < checkboxes.length; i++) {
           if (checkboxes[i].checked) {
            selectedItems.push(checkboxes[i].value);
          }
        }

        if (selectedItems.length === 0) {
            alert("주문할 상품을 선택하세요.");
            return;
        }

        $.ajax({
           url : '/order/from-cart',
           method: 'POST',
           contentType: 'application/json',
           data: JSON.stringify(selectedItems),
           success: function(data) {
               alert('주문 성공!');
           },
           error: function(xhr, status, error) {
                alert('주문 실패');
                console.error(error);
           }
        })

    }


</script>

<style>
    .list-table th,
    .list-table td {
        border: 2px solid #333;
    }
</style>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>