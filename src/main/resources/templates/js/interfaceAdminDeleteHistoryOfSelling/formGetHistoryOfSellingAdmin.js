$(document).ready(function (){
    $('#displaybar').append( "<form id='formGetHistoryOfSelling' style='margin: 10px;'>" +
        "   <select name='year' id='selectYear' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
        "<option>2021</option><option>2022</option><option>2023</option><option>2024</option><option>2025</option><option>2026</option><option>2027</option><option>2028</option><option>2029</option><option>2030</option>" +
        "<option>2031</option><option>2032</option><option>2033</option><option>2034</option><option>2035</option><option>2036</option><option>2037</option><option>2038</option><option>2039</option><option>2040</option>" +
        "</select>" +
        "   <select name='mounth' id='selectMounth' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
        "<option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option>" +
        "<option>11</option><option>12</option>" +
        "</select>" +
        "   <select name='day' id='selectDay' onClick='clickButtonGetHistory()' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
        "<option>01</option><option>02</option><option>03</option><option>04</option><option>05</option><option>06</option><option>07</option><option>08</option><option>09</option><option>10</option>" +
        "<option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option>" +
        "<option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option>" +
        "</select>" +
        "   </form>");
})

function clickButtonGetHistory(){
    $.ajax("http://localhost:8100/admin/getHistoryOfSelling",{
        type: 'post',
        data: $('#formGetHistoryOfSelling').serialize(),
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
                "<td style='border: 1px solid #545454; border-top-left-radius:30px; text-align: center; height: 50px; background-color: #545454; color: white'>Название</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Количество</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Дата</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Время</td>" +
                "<td style='border: 1px solid #545454; border-top-right-radius:30px; text-align: center; height: 50px; background-color: #545454; color: white'>Продавец</td>" +
                " </tr>";
            for (let i = 0; i < data.length; i++){
                if(i !== (data.length - 1)){
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].date +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].time +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].sellername + "</td>" +
                        "</tr>";
                } else {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].date +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].time +"</td>" +
                        "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].sellername + "</td>" +
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