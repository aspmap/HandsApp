function repeat() {
    var selector = 'audio';
    var audios = $(selector), len = audios.length, i = 0;
    audios.bind('ended', function (e) {
        i++;
        if (i === len) i = 0;
        audios[i].play();
    });
}