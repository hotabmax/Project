$(document).ready(function (){
    $('#displaybar').append( "<div>" +
        "<form id='formTranzactionDeleteProductAmountAndHistoryOfPurchase' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonTranzactionDeleteProductAmountAndHistoryOfPurchase' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'" +
        "        type='button' value='Списание товара со склада' onClick='clickTranzactionDeleteProductAmountAndHistoryOfPurchase()'; onMouseOver='eventMouseOverButtonTranzactionDeleteProductAmountAndHistoryOfPurchase()'; onMouseOut='eventMouseOutButtonTranzactionDeleteProductAmountAndHistoryOfPurchase()'>" +
        "        <select name='nameSort' id='selectSortToFindTDProduct' onClick='selectProductsForTranzactionDeleteProductAmountAndHistoryOfPurchase()' style='height: 50px; font-size: 16pt; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
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

function eventMouseOverButtonTranzactionDeleteProductAmountAndHistoryOfPurchase(){
    $('#buttonTranzactionDeleteProductAmountAndHistoryOfPurchase').css('backgroundColor', '#EEEEEF');
}

function eventMouseOutButtonTranzactionDeleteProductAmountAndHistoryOfPurchase(){
    $('#buttonTranzactionDeleteProductAmountAndHistoryOfPurchase').css('backgroundColor', '#f8f8f8');
}

function selectProductsForTranzactionDeleteProductAmountAndHistoryOfPurchase() {
    $.ajax( "http://localhost:8100/admin/getTableProductsBySort",{
        type: 'post',
        data: $('#formTranzactionDeleteProductAmountAndHistoryOfPurchase').serialize(),
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

function clickTranzactionDeleteProductAmountAndHistoryOfPurchase(){
    $.ajax("http://localhost:8100/admin/getTableProducts", {
        type: 'post',
        data: $('#formTranzactionDeleteProductAmountAndHistoryOfPurchase').serialize(),
        dataType: "json",
        success: function (data) {
            if (data[0].amount >= $('#amountAdd').val()) {
                $.ajax("http://localhost:8100/admin/tranzactionDeleteProductAmountAndHistoryOfPurchase", {
                    type: 'post',
                    data: $('#formTranzactionDeleteProductAmountAndHistoryOfPurchase').serialize(),
                    dataType: "json"
                })
                $('#warning').empty();
                setTimeout(function (){
                    $.ajax("http://localhost:8100/admin/getHistoryOfPurchase",{
                        type: 'post',
                        data: $('#formGetHistoryOfPurchase').serialize(),
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
                                "<td style='border: 1px solid #545454; border-top-right-radius:30px; text-align: center; height: 50px; background-color: #545454; color: white'>Логист</td>" +
                                " </tr>";
                            for (let i = 0; i < data.length; i++){
                                if(i !== (data.length - 1)){
                                    html += "<tr>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount +"</td>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].date +"</td>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].time +"</td>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].logistname + "</td>" +
                                        "</tr>";
                                } else {
                                    html += "<tr>" +
                                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount +"</td>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].date +"</td>" +
                                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].time +"</td>" +
                                        "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].logistname + "</td>" +
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
                }, 100 )
            } else {
                $('#warning').empty();
                $('#warning').prepend("На складе " + data[0].name + " осталось всего: " + data[0].amount + "!!!");
            }
        }
    })
}