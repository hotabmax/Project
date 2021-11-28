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
function eventMouseOverButtonAddUser(){
    $('#buttonAddUser').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonAddUser(){
    $('#buttonAddUser').css('backgroundColor', '#f8f8f8');
}

function clickCreateUser(){
    $.ajax("http://localhost:8100/admin/createUser",{
        type: 'post',
        data: $('#formCreateUser').serialize(),
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
            if($('table').is('#t')){
                document.getElementById('t').remove();
            }
            div.append(table);
        }
    })
}