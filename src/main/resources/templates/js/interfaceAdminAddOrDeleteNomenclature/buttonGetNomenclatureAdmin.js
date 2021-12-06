$(document).ready(function (){
    $('#menubar').append("<input id='buttonGetNomenclature' type='button' value='Номенклатура' " +
        "style='width: 200px; height: 50px; font-size: 16pt; float: left; border: 0px; border-radius: 10px; background-color: white; margin-left: 25px; margin-top: 25px;' onClick='clickGetNomenclature()'; onMouseOver='eventMouseOverButtonGetNomenclature()'; onMouseOut='eventMouseOutButtonGetNomenclature()'/>"
    )
})
function eventMouseOverButtonGetNomenclature(){
    $('#buttonGetNomenclature').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonGetNomenclature(){
    $('#buttonGetNomenclature').css('backgroundColor', 'white');
}
function clickGetNomenclature() {
    $.ajax({
        url: "http://localhost:8100/adminAddOrDeleteNomenclature",
        type: 'get',
        success: function (data){
            $('body').fadeOut(200, function (){
                $('body').html(data);
            });
            $('body').fadeIn(200);
        }
    })
}