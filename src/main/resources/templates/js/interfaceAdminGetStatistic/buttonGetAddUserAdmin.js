$(document).ready(function (){
    $('#menubar').append("<input id='buttonGetAddUser' type='button' value='Пользователи' " +
        "style='width: 200px; height: 50px; font-size: 16pt; float: left; border: 0px; border-radius: 10px; background-color: white; margin-left: 25px; margin-top: 25px;' onClick='clickGetAddUser()'; onMouseOver='eventMouseOverButtonGetAddUser()'; onMouseOut='eventMouseOutButtonGetAddUser()'/>"
    )
})
function eventMouseOverButtonGetAddUser(){
    $('#buttonGetAddUser').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonGetAddUser(){
    $('#buttonGetAddUser').css('backgroundColor', 'white');
}
function clickGetAddUser(){
    $.ajax({
        url: "http://localhost:8100/adminAddOrDeleteUser",
        type: 'get',
        success: function (data){
            $('body').fadeOut(200, function (){
                $('body').html(data);
            });
            $('body').fadeIn(200);
        }
    })
}
