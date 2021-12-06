$(document).ready(function (){
    $('#menubar').append("<input id='buttonExit' type='button' value='Выход' " +
        "style='width: 200px; height: 50px; font-size: 16pt; float: right; border: 0px; border-radius: 10px; background-color: white; margin-right: 25px; margin-top: 25px;' onClick='clickExit()'; onMouseOver='eventMouseOverButtonExit()'; onMouseOut='eventMouseOutButtonExit()'/>"
    )
})
function clickExit(){
    $.ajax({
        url: "http://localhost:8100/exit",
        type: 'get',
        success: function (){
            location.reload()
        }
    })
}
function eventMouseOverButtonExit(){
    $('#buttonExit').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonExit(){
    $('#buttonExit').css('backgroundColor', 'white');
}