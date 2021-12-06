$(document).ready(function (){
    $('#menubar').append("<input id='buttonGetStatistic' type='button' value='Прибыль' " +
        "style='width: 200px; height: 50px; font-size: 16pt; float: left; border: 0px; border-radius: 10px; background-color: white; margin-left: 10px; margin-top: 25px;' onClick='clickGetStatistic()'; onMouseOver='eventMouseOverButtonGetStatistic()'; onMouseOut='eventMouseOutButtonGetStatistic()'/>"
    )
})
function eventMouseOverButtonGetStatistic(){
    $('#buttonGetStatistic').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonGetStatistic(){
    $('#buttonGetStatistic').css('backgroundColor', 'white');
}
function clickGetStatistic(){
    $.ajax({
        url: "http://localhost:8100/adminGetStatistic",
        type: 'get',
        success: function (data){
            $('body').fadeOut(200, function (){
                $('body').html(data);
            });
            $('body').fadeIn(200);
        }
    })
}