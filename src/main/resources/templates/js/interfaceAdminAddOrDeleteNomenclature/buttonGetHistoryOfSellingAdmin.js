$(document).ready(function (){
    $('#menubar').append("<input id='buttonGetHistoryOfSelling' type='button' value='Продажа товара' " +
        "style='width: 200px; height: 50px; font-size: 16pt; float: left; border: 0px; border-radius: 10px; background-color: white; margin-left: 25px; margin-top: 25px;' onClick='clickGetHistoryOfSelling()'; onMouseOver='eventMouseOverButtonGetHistoryOfSelling()'; onMouseOut='eventMouseOutButtonGetHistoryOfSelling()'/>"
    )
})
function eventMouseOverButtonGetHistoryOfSelling(){
    $('#buttonGetHistoryOfSelling').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonGetHistoryOfSelling(){
    $('#buttonGetHistoryOfSelling').css('backgroundColor', 'white');
}

function clickGetHistoryOfSelling() {
    $.ajax({
        url: "http://localhost:8100/adminDeleteHistoryOfSelling",
        type: 'get',
        success: function (data){
            $('body').fadeOut(200, function (){
                $('body').html(data);
            });
            $('body').fadeIn(200);
        }
    })
}
