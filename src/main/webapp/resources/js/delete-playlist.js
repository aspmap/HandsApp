function deletePlaylist(playlistId) {
    fetch(contextPath + "/music/playlist/delete/" + playlistId, {method: "POST"})
        .then((response) => {
            if (response.status == 200 && response.redirected != true){
                var element = document.getElementById("playlist-" + playlistId);
                element.parentNode.removeChild(element);
            }
        })
}