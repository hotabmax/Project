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

    $('#displaybar').remove();
    $('#databar').remove();

    $(document).ready(function (){
        $('body').append("<div id='displaybar' style='width: 90%; min-height: 400px; font-family: Arial; float: left; background-color: #f8f8f8; border-radius: 30px; margin-top: 50px; margin-left: 5%; padding-left: 50px'>" +
            "<div style='text-align: center;'><h1>Панель управления</h1></div>" +
            "</div>")
    })

    $(document).ready(function (){
        $('#displaybar').append( "<form id='formGetHistoryOfSelling' style='margin: 10px;'>" +
            "   <select name='year' id='selectYear' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
            "<option>2021</option><option>2022</option><option>2023</option><option>2024</option><option>2025</option><option>2026</option><option>2027</option><option>2028</option><option>2029</option><option>2030</option>" +
            "<option>2031</option><option>2032</option><option>2033</option><option>2034</option><option>2035</option><option>2036</option><option>2037</option><option>2038</option><option>2039</option><option>2040</option>" +
            "</select>" +
            "   <select name='mounth' id='selectMounth' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
            "<option>1</option><option>2</option><option>3</option><option>4</option><option>5</option><option>6</option><option>7</option><option>8</option><option>9</option><option>10</option>" +
            "<option>11</option><option>12</option>" +
            "</select>" +
            "   <select name='day' id='selectDay' onClick='clickButtonGetHistory()' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'>" +
            "<option>1</option><option>2</option><option>3</option><option>4</option><option>5</option><option>6</option><option>7</option><option>8</option><option>9</option><option>10</option>" +
            "<option>11</option><option>12</option><option>13</option><option>14</option><option>15</option><option>16</option><option>17</option><option>18</option><option>19</option><option>20</option>" +
            "<option>21</option><option>22</option><option>23</option><option>24</option><option>25</option><option>26</option><option>27</option><option>28</option><option>29</option><option>30</option><option>31</option>" +
            "</select>" +
            "   </form>");
    })

    $(document).ready(function (){
        $('#displaybar').append( "<div>" +
            "<form id='formTranzactionDeleteHistoryOfSelling' style='display: inline-block; margin: 10px;'>" +
            "        <input id='buttonTranzactionDeleteHistoryOfSelling' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'" +
            "        type='button' value='Списание товара со склада' onClick='clickTranzactionDeleteHistoryOfSelling()'; onMouseOver='eventMouseOverButtonTranzactionDeleteHistoryOfSelling()'; onMouseOut='eventMouseOutButtonTranzactionDeleteHistoryOfSelling()'>" +
            "        <select name='nameSort' id='selectSortToFindTDProduct' onClick='selectProductTranzactionDeleteHistoryOfSelling()' style='height: 50px; font-size: 16pt; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
            "        <select name='name' id='selectTDProduct' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
            "        <input id='amountAdd' name='amount' placeholder='Количество' style='width: 120px; height: 45px; font-size: 16pt; text-align: center; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "<p id='warning' style='font-size: 16pt'></p>" +
            "        </form>" +
            "</div>");
        $.ajax( "http://localhost:8100/admin/getTableSorts",{
            type: 'post',
            dataType: "json",
            success: function (data) {
                let sel = document.getElementById('selectSortToFindTDProduct');
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel.prepend(opt);
                }
            }
        })
    })

    $(document).ready(function (){
        $('body').append("<div id='databar'; style='width: 90%; min-height: 300px; font-family: Arial; float: left ; background-color: #f8f8f8; border-radius: 30px; margin-top: 50px; margin-left: 5%; padding-bottom: 50px'>" +
            "<div style='text-align: center;'><h1>История</h1></div>" +
            "</div>")
    })
}
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

function eventMouseOverButtonTranzactionDeleteHistoryOfSelling(){
    $('#buttonTranzactionDeleteHistoryOfSelling').css('backgroundColor', '#EEEEEF');
}

function eventMouseOutButtonTranzactionDeleteHistoryOfSelling(){
    $('#buttonTranzactionDeleteHistoryOfSelling').css('backgroundColor', '#f8f8f8');
}

function selectProductTranzactionDeleteHistoryOfSelling() {
    $.ajax( "http://localhost:8100/admin/getTableProductsBySort",{
        type: 'post',
        data: $('#formTranzactionDeleteHistoryOfSelling').serialize(),
        dataType: "json",
        success: function (data) {
            let sel = document.getElementById('selectTDProduct');
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

function clickTranzactionDeleteHistoryOfSelling(){
    $.ajax("http://localhost:8100/admin/getTableProducts", {
        type: 'post',
        data: $('#formTranzactionDeleteHistoryOfSelling').serialize(),
        dataType: "json",
        success: function (data) {
            if (data[0].amount >= $('#amountAdd').val()) {
                $.ajax("http://localhost:8100/admin/tranzactionDeleteHistoryOfSelling", {
                    type: 'post',
                    data: $('#formTranzactionDeleteHistoryOfSelling').serialize(),
                    dataType: "json"
                })
                $('#warning').empty();
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
            } else {
                $('#warning').empty();
                $('#warning').prepend("На складе " + data[0].name + " осталось всего: " + data[0].amount + "!!!");
            }
        }
    })
}