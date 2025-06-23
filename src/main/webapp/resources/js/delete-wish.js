function deleteWish(wishId) {
    fetch(contextPath + "/wishlist/delete/" + wishId, {method: "DELETE"})
        .then((response) => {
            if (response.status == 200 && response.redirected != true){
                var element = document.getElementById("wishlist-" + wishId);
                element.parentNode.removeChild(element);
            }
        })
}