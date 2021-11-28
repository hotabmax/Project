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
function eventMouseOverButtonDeleteUser(){
    $('#buttonDeleteUser').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonDeleteUser(){
    $('#buttonDeleteUser').css('backgroundColor', '#f8f8f8');
}

function selectProductsForDelete() {
    $.ajax({
        url: "http://localhost:8100/admin/getTableUsersByRole",
        type: 'post',
        data: $('#formDeleteUser').serialize(),
        dataType: "json",
        success: function (data) {
            let sel = document.getElementById('selectDeletedUser');
            while (sel.firstChild) {
                sel.removeChild(sel.firstChild);
            }
            for (let i = 0; i < data.length; i++) {
                let name = data[i].name;
                let opt = document.createElement('option');
                opt.innerHTML = name;
                sel.prepend(opt);
            }
        }
    })
}
function clickDeleteUser() {
    $.ajax("http://localhost:8100/admin/deleteUser", {
        type: 'post',
        data: $('#formDeleteUser').serialize(),
        dataType: "json",
        success: function (data) {
            let table = document.createElement('table');
            table.id = "t";
            table.style.width = "90%";
            table.style.marginLeft = "5%";
            table.style.marginTop = "5%";
            table.style.borderSpacing = "0px";
            table.style.fontSize = "16pt";
            let html = "<tr> " +
                "<td style='border: 1px solid #545454; border-top-left-radius:30px; text-align: center; height: 50px; background-color: #545454; color: white'>Имя</td>" +
                "<td style='border: 1px solid #545454; border-top-right-radius:30px; text-align: center; height: 50px; background-color: #545454; color: white'>Пароль</td>" +
                " </tr>";
            for (let i = 0; i < data.length; i++){
                if(i !== (data.length - 1)){
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].password + "</td>" +
                        "</tr>";
                } else {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].password + "</td>" +
                        "</tr>";
                }

            }
            table.innerHTML = html;
            let div = document.getElementById('databar');
            if ($('table').is('#t')) {
                document.getElementById('t').remove();
            }
            div.append(table);
        }
    })
}