<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/check" method="post">
        <label for="productId1">Product Id
            <input type="text" name="id1" id="productId1">
        </label>
        <label for="productQuantity1">Product quantity
            <input type="text" name="quantity1" id="productQuantity1">
        </label>
        <br>
        <br>
        <label for="productId2">Product Id
            <input type="text" name="id2" id="productId2">
        </label>
        <label for="productQuantity2">Product quantity
            <input type="text" name="quantity2" id="productQuantity2">
        </label>
        <br>
        <br>
        <label for="productId3">Product Id
            <input type="text" name="id3" id="productId3">
        </label>
        <label for="productQuantity3">Product quantity
            <input type="text" name="quantity3" id="productQuantity3">
        </label>
        <br>
        <br>
        <label for="discountCard">Discount card No
            <input type="text" name="discountCard" id="discountCard">
        </label>
        <br>
        <br>
        <button type="submit">
            Send your order
        </button>
    </form>
    <br>
    <br>
    <button type="button">
        <a href="/check?id=3-1&id=2-5&id=6-8&id=9-4&id=5-1&id=card-123">Print a default check</a>
    </button>

</body>
</html>
