function addToCart(prodNo) {
    return fetch(`/cart/add?prodNo=${prodNo}`).then(res => res.json());
}

window.addToCart = addToCart;