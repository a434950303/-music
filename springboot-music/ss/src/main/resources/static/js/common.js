var songs;
function playMusic(name) {//调用方法，更改cookie的信息，并跳转到播放页面
    var currentSong;
    if (name) {
        for (let i = 0; i < songs.list.length; i++) {
            if (songs.list[i].ml_songName == name) {
                currentSong = songs.list[i];
                break;
            }
        }
    } else {
        var x = 0;
        var y = songs.list.length;
        var result = Math.floor(Math.random() * (y + 1 - x) + x);
        currentSong = songs.list[result];
    }
    $.cookie("playMusic", "playMusic", {expires: 7, path: "/"});
    $.cookie("song_link", currentSong.ml_songLink, {expires: 7, path: "/"});
    $.cookie("song_name", currentSong.ml_songName, {expires: 7, path: "/"});
    $.cookie("song_singer", currentSong.ml_singer, {expires: 7, path: "/"});
    $.cookie("song_photo", currentSong.ml_photoLink, {expires: 7, path: "/"});
    $.cookie("currentSong", JSON.stringify(currentSong), {expires: 7, path: "/"});
    window.location.href = "/QQmusic.html";
}


function addSongToSongList(songId) {//调用方法，更改cookie的信息，并跳转到播放页面

    if (!isBlank($.cookie("user_id"))) {
        $.ajax({
            url: "myMusic/getMySongListNames",      //后台获取整个数据库方法的地址
            type: "POST",
            data: {
                "user_id": $.cookie("user_id")
            },
            success: function (data) {
                var options = JSON.parse(data.data.toString())
                jSelect('请选择歌单:', options, '歌单', function (inputdata) {
                    if (isBlank(inputdata)){

                    }else{
                        $.ajax({
                            url: "myMusic/addSongToSongList",      //后台获取整个数据库方法的地址
                            type: "POST",
                            data: {
                                "song_id": songId,
                                "song_list_id":inputdata
                            },
                            success: function (data) {
                              alert(data.data)
                            },
                            error: function (data) {
                                alert(JSON.stringify(data));//连接失败弹窗
                            }
                        })
                    }
                });
            },
            error: function (data) {
                alert(JSON.stringify(data));//连接失败弹窗
            }
        })
    } else {
        alert("请先登录再收藏。");
    }


}

function isBlank(str) {
    if (str != null && str != 'null') {
        return false;
    }
    return true;
}

function changeTitle(title) {
    document.getElementById("myTitle").innerText=title;
}


function searchBySinger(singerName) {//调用方法，更改cookie的信息，并跳转到播放页面
    $.ajax({
        url: "musicLink/getSongRearchBySinger",
        type: "POST",
        data: "singerName="+singerName,
        success: function (data) {
            if (data.statusCode == "200") {
                var search = singerName;
                $.cookie("singer_search", search, {expires: 7, path: "/"});
                $.cookie('song_search',null,{ path: '/'});
                window.location.href="/musicSearchTest.html";
                var str = '';
                /* data.data.list.length对应respon.map.list.length */
                for (var i = 0; i < data.data.list.length; i++) {
                    str += '<tr>'
                        + '<td>' + data.data.list[i].ml_songName + '</td>'
                        + '<td>' + data.data.list[i].ml_singer + '</td>'
                        /* + '<td>' + data.data.list[i].ml_songLink + '</td>' */
                        + '</tr>';
                }
                $("table tbody").html(str);
            } else if (data.statusCode == "202") {
                alert("搜索不到歌曲,请重新输入！");
            }
        },
        error: function (data) {
            alert(JSON.stringify(data));
        }
    })
}

function delMusic(song_list_id,ml_id) {//删除已收藏歌曲的方法
    $.ajax({
        async: false,//给ajax嵌套执行，所以需设定false
        url: "/myMusic/deleteMyMusic",//后台地址
        type: "post",//post请求方式
        data: {
            "song_list_id": song_list_id,//传入歌曲id
            "ml_id": ml_id,//传入用户id
        },
        success: function (data) {//webspond
                window.location.reload();//刷新页面
                alert("已成功从收藏列表移除");//提示删除成功
                location.reload();

        },

    })
}


(function ($) {

    $.alerts = {

        // These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time

        verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
        horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
        repositionOnResize: true,           // re-centers the dialog on window resize
        overlayOpacity: .01,                // transparency level of overlay
        overlayColor: '#FFF',               // base color of overlay
        draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
        okButton: ' 确认 ',         // text for the OK button
        cancelButton: ' 取消 ', // text for the Cancel button
        dialogClass: null,                  // if specified, this class will be applied to all dialogs

        // Public methods

        alert: function (message, title, callback) {
            if (title == null) title = 'Alert';
            $.alerts._show(title, message, null, 'alert', function (result) {
                if (callback) callback(result);
            });
        },

        confirm: function (message, title, callback) {
            if (title == null) title = 'Confirm';
            $.alerts._show(title, message, null, 'confirm', function (result) {
                if (callback) callback(result);
            });
        },

        prompt: function (message, value, title, callback) {
            if (title == null) title = 'Prompt';
            $.alerts._show(title, message, value, 'prompt', function (result) {
                if (callback) callback(result);
            });
        },
        select: function (message, value, title, callback) {
            if (title == null) title = 'Select';
            $.alerts._show(title, message, value, 'select', function (result) {
                if (callback) callback(result);
            });
        },
        // Private methods

        _show: function (title, msg, value, type, callback) {

            $.alerts._hide();
            $.alerts._overlay('show');

            $("BODY").append(
                '<div id="popup_container">' +
                '<h1 id="popup_title"></h1>' +
                '<div id="popup_content">' +
                '<div id="popup_message"></div>' +
                '</div>' +
                '</div>');

            if ($.alerts.dialogClass) $("#popup_container").addClass($.alerts.dialogClass);


            $("#popup_container").css({
                position: "absolute",
                zIndex: 99999,
                padding: 0,
                margin: 0
            });

            $("#popup_title").text(title);
            $("#popup_content").addClass(type);
            $("#popup_message").text(msg);
            $("#popup_message").html($("#popup_message").text().replace(/\n/g, '<br />'));

            $("#popup_container").css({
                minWidth: $("#popup_container").outerWidth(),
                maxWidth: $("#popup_container").outerWidth()
            });

            $.alerts._reposition();
            $.alerts._maintainPosition(true);

            switch (type) {
                case 'alert':
                    $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        callback(true);
                    });
                    $("#popup_ok").focus().keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) $("#popup_ok").trigger('click');
                    });
                    break;
                case 'confirm':
                    $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        if (callback) callback(true);
                    });
                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(false);
                    });
                    $("#popup_ok").focus();
                    $("#popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });
                    break;
                case 'prompt':
                    $("#popup_message").append('<br /><input type="text" size="30" id="popup_prompt" />').after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
                    $("#popup_prompt").width($("#popup_message").width());
                    $("#popup_ok").click(function () {
                        var val = $("#popup_prompt").val();
                        $.alerts._hide();
                        if (callback) callback(val);
                    });
                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(null);
                    });
                    $("#popup_prompt, #popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });
                    if (value) $("#popup_prompt").val(value);
                    $("#popup_prompt").focus().select();
                    break;
                case 'select':
                    var result = '';
                    var flag = true;
                    for (var key in value) {
                        if (flag) {
                            flag = false;
                            result += ('<option selected="selected" value="' + key + '">' + value[key] + '</option>');
                        } else {
                            result += ('<option value="' + key + '">' + value[key] + '</option>');
                        }
                    }
                    $("#popup_message").append('<br /><select id="popup_select">' + result + '</select>').after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" /> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" /></div>');
                    $("#popup_select").width($("#popup_message").width());
                    $("#popup_ok").click(function () {
                        var val = $("#popup_select").val();
                        $.alerts._hide();
                        if (callback) {
                            callback(val);
                        }
                        ;
                    });
                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(null);
                    });
                    $("#popup_select, #popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });

                    $("#popup_select").focus().select();
                    break;
            }

            // Make draggable
            if ($.alerts.draggable) {
                try {
                    $("#popup_container").draggable({handle: $("#popup_title")});
                    $("#popup_title").css({cursor: 'move'});
                } catch (e) { /* requires jQuery UI draggables */
                }
            }
        },

        _hide: function () {
            $("#popup_container").remove();
            $.alerts._overlay('hide');
            $.alerts._maintainPosition(false);
        },

        _overlay: function (status) {
            switch (status) {
                case 'show':
                    $.alerts._overlay('hide');
                    $("BODY").append('<div id="popup_overlay"></div>');
                    $("#popup_overlay").css({
                        position: 'absolute',
                        zIndex: 99998,
                        top: '0px',
                        left: '0px',
                        width: '100%',
                        height: $(document).height(),
                        background: $.alerts.overlayColor,
                        opacity: $.alerts.overlayOpacity
                    });
                    break;
                case 'hide':
                    $("#popup_overlay").remove();
                    break;
            }
        },

        _reposition: function () {
            var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
            var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
            if (top < 0) top = 0;
            if (left < 0) left = 0;

            // IE6 fix
            top = top + $(window).scrollTop();

            $("#popup_container").css({
                top: top + 'px',
                left: left + 'px'
            });
            $("#popup_overlay").height($(document).height());
        },

        _maintainPosition: function (status) {
            if ($.alerts.repositionOnResize) {
                switch (status) {
                    case true:
                        $(window).bind('resize', function () {
                            $.alerts._reposition();
                        });
                        break;
                    case false:
                        $(window).unbind('resize');
                        break;
                }
            }
        }
    }
    jAlert = function (message, title, callback) {
        $.alerts.alert(message, title, callback);
    }
    jConfirm = function (message, title, callback) {
        $.alerts.confirm(message, title, callback);
    };
    jPrompt = function (message, value, title, callback) {
        $.alerts.prompt(message, value, title, callback);
    };
    jSelect = function (message, value, title, callback) {
        $.alerts.select(message, value, title, callback);
    };
})(jQuery);
