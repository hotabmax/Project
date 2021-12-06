$(document).ready(function (){
    $('#menubar').append("<input id='buttonGetHistoryOfPurchase' type='button' value='Приход товара' " +
        "style='width: 200px; height: 50px; font-size: 16pt; float: left; border: 0px; border-radius: 10px; background-color: white; margin-left: 25px; margin-top: 25px;' onClick='clickGetHistoryOfPurchase()'; onMouseOver='eventMouseOverButtonGetHistoryOfPurchase()'; onMouseOut='eventMouseOutButtonGetHistoryOfPurchase()'/>"
    )
})
function eventMouseOverButtonGetHistoryOfPurchase(){
    $('#buttonGetHistoryOfPurchase').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonGetHistoryOfPurchase(){
    $('#buttonGetHistoryOfPurchase').css('backgroundColor', 'white');
}

function clickGetHistoryOfPurchase() {
    $.ajax({
        url: "http://localhost:8100/adminDeleteHistoryOfPurchase",
        type: 'get',
        success: function (data){
            $('body').fadeOut(200, function (){
                $('body').html(data);
            });
            $('body').fadeIn(200);
        }
    })
}
