$(document).ready(function (){
    $('#displaybar').append( "<form id='formGetHistoryOfSelling' style='margin: 10px;'>" +
        "   <select name='year' id='selectYear' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
        "<option>2021</option><option>2022</option><option>2023</option><option>2024</option><option>2025</option><option>2026</option><option>2027</option><option>2028</option><option>2029</option><option>2030</option>" +
        "<option>2031</option><option>2032</option><option>2033</option><option>2034</option><option>2035</option><option>2036</option><option>2037</option><option>2038</option><option>2039</option><option>2040</option>" +
        "</select>" +
        "   <select name='mounth' id='selectMounth' onClick='clickButtonGetStatistic()' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
        "<option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option>" +
        "<option>11</option><option>12</option>" +
        "</select>" +
        "   </form>");
})

function clickButtonGetStatistic() {
    $.ajax("http://localhost:8100/admin/getStatistic",{
        type: 'post',
        data: $('#formGetHistoryOfSelling').serialize(),
        dataType: "text",
        success: function (data) {
            $('#t').remove();
            $('#displaybar').append("<div id='t' style='font-size: 16pt; padding-left: 15px'>" + data + "</div>");
        },
        error: function (){
            $('#t').remove();
        }
    })
}