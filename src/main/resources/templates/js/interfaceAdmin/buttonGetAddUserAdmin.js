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
    $('#displaybar').remove();
    $('#databar').remove();

    $(document).ready(function (){
        $('body').append("<div id='displaybar' style='width: 90%; min-height: 400px; font-family: Arial; float: left; background-color: #f8f8f8; border-radius: 30px; margin-top: 50px; margin-left: 5%; padding-left: 50px'>" +
            "<div style='text-align: center;'><h1>Панель управления</h1></div>" +
            "</div>")
    })

    $(document).ready(function (){
        $('body').append("<div id='databar'; style='width: 90%; min-height: 300px; font-family: Arial; float: left ; background-color: #f8f8f8; border-radius: 30px; margin-top: 50px; margin-left: 5%; padding-bottom: 50px'>" +
            "<div style='text-align: center;'><h1>Пользователи</h1></div>" +
            "</div>")
    })

    $(document).ready(function (){
        $('#displaybar').append( "<form id='formGetUsersWithRole' style='margin: 10px;'>" +
            "   <select name='nameRole' id='selectRole' onClick='clickButtonGetUsersWithRole()' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'></select>" +
            "   </form>");

        $.ajax({
            url: "http://localhost:8100/admin/getTableRoles",
            type: 'post',
            dataType: "json",
            success: function (data) {
                let sel = document.getElementById('selectRole');
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel.prepend(opt);
                }
            }
        })
    })

    $(document).ready(function (){
        $('#displaybar').append( "<div>" +
            "<form id='formCreateUser' style='display: inline-block; margin: 10px;'>" +
            "        <input id='buttonAddUser' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
            "        type='button' value='Добавить пользователя' onClick='clickCreateUser()'; onMouseOver='eventMouseOverButtonAddUser()'; onMouseOut='eventMouseOutButtonAddUser()'>" +
            "        <input name='name' placeholder='Имя' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <input name='password' placeholder='Пароль' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <select name='nameRole' id='selectRoleToAddUser' style='height: 50px; font-size: 16pt; text-align: center; outline: none; background-color: #f8f8f8; border: 0px; padding-right: 5px' ></select>" +
            "        </form>" +
            "</div>");
        $.ajax({
            url: "http://localhost:8100/admin/getTableRoles",
            type: 'post',
            dataType: "json",
            success: function (data) {
                let sel = document.getElementById('selectRoleToAddUser');
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel.prepend(opt);
                }
            }
        })
    })

    $(document).ready(function (){
        $('#displaybar').append( "<div>" +
            "<form id='formDeleteUser' style='display: inline-block; margin: 10px;'>" +
            "        <input id='buttonDeleteUser' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
            "        type='button' value='Удалить пользователя' onClick='clickDeleteUser()'; onMouseOver='eventMouseOverButtonDeleteUser()'; onMouseOut='eventMouseOutButtonDeleteUser()'>" +
            "        <select name='nameRole' id='selectRoleToDeleteUser' onClick='selectProductsForDelete()' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
            "        <select name='name' id='selectDeletedUser' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
            "        </form>" +
            "</div>");
        $.ajax({
            url: "http://localhost:8100/admin/getTableRoles",
            type: 'post',
            dataType: "json",
            success: function (data) {
                let sel = document.getElementById('selectRoleToDeleteUser');
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel.prepend(opt);
                }
            }
        })
    })
}
