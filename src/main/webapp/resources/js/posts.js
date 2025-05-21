function posts() {
    const username = user;
    var token = bToken;
    $.ajax({
            url: msServiceUrl + '/posts',
            type: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            dataType: 'json',
            success: function (data) {
                data.forEach(el => {

                    var post = $('<div class="post"></div>');
                    var info = $('<div class="info"></div>');
                    var user = $('<div class="user"></div>');
                    var pic = $('<div class="profile-pic"></div>')

                    post.append(info);
                    info.append(user);
                    user.append(pic);

                    var img = $('<img src="' + contextPath + "/resources/img/users/" + username + "/profile/" + userPhoto + '" alt="" />');

                    var user_name = $('<p class="username">' + username + '</p>')

                    pic.append(img);
                    user.append(user_name);

                    var ahref = $('<a href="' + contextPath + "/post/" + el.postId + '"></a>');

                    if (el.storageType == 'S3' && el.extFile == 'png') {
                        var imgpost = $('<img class="post-image" src="' + el.photo + '" alt="" />');
                    } else if (el.storageType != 'S3' && el.extFile == 'png') {
                        var imgpost = $('<img class="post-image" src="' + contextPath + "/resources/img/users/" + username + "/" + el.photo + '" alt="" />');
                    }
                    post.append(ahref);
                    ahref.append(imgpost);

                    if (el.extFile == 'mp4' || el.extFile == 'mov') {
                        var videopost = $('<video width = "100%" autoPlay controls loop muted alt=""></video>');
                        var videoSource = $('<source src="' + contextPath + "/resources/video/users/" + username + "/" + el.photo + '" type = video/mp4 >');
                        ahref.append(videopost);
                        videopost.append(videoSource);
                    }

                    var postContent = $('<div class="post-content"></div>');
                    var desc = $('<b class="description_ms"></b>');
                    var span = $('<span >' + username + '</span>');
                    var content = $('<span class="description-normal" >' + el.content + '</span>');
                    var date_dmy = new Date(el.createdAt);
                    var date_result = date_dmy.getDate() + '.' + date_dmy.getMonth() + '.' + date_dmy.getFullYear();
                    var dateContent = $('<p class="post-time">' + date_result + ' г.</p>');
                    post.append(postContent);
                    postContent.append(desc);
                    desc.append(span);
                    postContent.append(content);
                    postContent.append(dateContent);

                    $('#posts').append(post);
                });
            },
            error: function (error) {
                var br = $('<br><br><br><br><br><br>');
                var textError = $('<h1 class="h3 mb-3 font-weight-normal text-center">Сервис временно недоступен. Попробуйте позже</h1>');
                var br2 = $('<br><br><br><br>');
                $('#br').append(br);
                $('#error').append(textError);
                $('#br2').append(br2);
            }
        }
    );
}