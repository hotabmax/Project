$(document).ready(function (){
    $('body').append("<div style='width: 40%; min-height: 300px; font-family: Arial; background-color: #f8f8f8; border-radius: 30px; margin-top: 50px; margin-left: 30%; padding-top: 25px'>" +
        "<div style='text-align: center;'><h1>Страница авторизации</h1></div>" +
        "<form method='post' action='autorization'>" +
        "<input name='name' placeholder='логин' style='display: block; width: 40%; height: 45px; transition: 1000s; outline: none; background-color: #f8f8f8; font-size: 16pt; margin: 10px; margin-top: 40px; margin-left: 30%;  border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'/>" +
        "<input name='password' placeholder='пароль' style='display: block; width: 40%; height: 45px; transition: 1000s; outline: none; background-color: #f8f8f8; font-size: 16pt; margin: 10px; margin-left: 30%; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'/>" +
        "<input id='buttonAutorization' type='submit' value='Авторизация' style='display: block; width: 42%; height: 45px; background-color: #f8f8f8; font-size: 16pt; margin: 10px; margin-left: 30%; border: 0px; border-radius:10px; text-align: center'; onMouseOver='eventOverButtonAutorization()'; onMouseOut='eventOutButtonAutorization()'/>" +
        "</form>" +
        "</div>")
})
function eventOverButtonAutorization(){
    $('#buttonAutorization').css('backgroundColor', '#EEEEEF');
}
function eventOutButtonAutorization(){
    $('#buttonAutorization').css('backgroundColor', '#f8f8f8');
}