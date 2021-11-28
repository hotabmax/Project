$(document).ready(function (){
    $('#displaybar').append( "<div>" +
        "<form id='formAddToBasket' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonAddToBasket' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'" +
        "        type='button' value='Добавить в корзину' onClick='clickTranzactionAddBasket()'; onMouseOver='eventMouseOverButtonAddToBasket()'; onMouseOut='eventMouseOutButtonAddToBasket()'>" +
        "        <select name='nameSort' id='selectSortToFindTDProduct' onClick='selectProductsForTransactionDelete()' style='height: 50px; font-size: 16pt; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
        "        <select name='name' id='selectTDProduct' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
        "        <input id='amountAdd' name='amount' placeholder='Количество' style='width: 120px; height: 45px; font-size: 16pt; text-align: center; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
        "<p id='warning' style='font-size: 16pt'></p>" +
        "        </form>" +
        "</div>");
    $.ajax({
        url: "http://localhost:8100/seller/getTableSorts",
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
    $('#displaybar').append("<form id='formTranzactionDeleteProduct' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonTranzactionDelete' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'" +
        "        type='button' value='Оплата' onClick='clickTranzactionDeleteProduct()'; onMouseOver='eventMouseOverButtonTranzactionDelete()'; onMouseOut='eventMouseOutButtonTranzactionDelete()'>" +
        "        </form>"
    );
    $('#displaybar').append("<form id='formClearBasket' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonClearBasket' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'" +
        "        type='button' value='Отчистить корзину' onClick='clickClearBasket()'; onMouseOver='eventMouseOverButtonClearBasket()'; onMouseOut='eventMouseOutButtonClearBasket()'>" +
        "        </form>"
    );
    $('#displaybar').append("<form id='formDeleteLastItemOfBasket' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonDeleteLastItemOfBasket' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'" +
        "        type='button' value='Удалить последний элемента из корзины' onClick='clickDeleteLastItemOfBasket()'; onMouseOver='eventMouseOverButtonDeleteLastItemOfBasket()'; onMouseOut='eventMouseOutButtonDeleteLastItemOfBasket()'>" +
        "        </form>"
    );
})
function eventMouseOverButtonAddToBasket(){
    $('#buttonAddToBasket').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonAddToBasket(){
    $('#buttonAddToBasket').css('backgroundColor', '#f8f8f8');
}

function eventMouseOverButtonTranzactionDelete(){
    $('#buttonTranzactionDelete').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonTranzactionDelete(){
    $('#buttonTranzactionDelete').css('backgroundColor', '#f8f8f8');
}

function eventMouseOverButtonClearBasket(){
    $('#buttonClearBasket').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonClearBasket(){
    $('#buttonClearBasket').css('backgroundColor', '#f8f8f8');
}

function eventMouseOverButtonDeleteLastItemOfBasket(){
    $('#buttonDeleteLastItemOfBasket').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonDeleteLastItemOfBasket(){
    $('#buttonDeleteLastItemOfBasket').css('backgroundColor', '#f8f8f8');
}

function selectProductsForTransactionDelete() {
    $.ajax({
        url: "http://localhost:8100/seller/getTableProductsBySort",
        type: 'post',
        data: $('#formAddToBasket').serialize(),
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

dataOfBasket = new Map;
function clickTranzactionAddBasket(){
    $.ajax("http://localhost:8100/seller/getTableProducts", {
        type: 'post',
        data: $('#formAddToBasket').serialize(),
        dataType: "json",
        success: function (data) {
            if (data[0].amount >= $('#amountAdd').val()) {
                if(dataOfBasket.size !== 0){
                    let flag;
                    for(let i = 0; i < dataOfBasket.size; i++){
                        if(dataOfBasket.get(i).name === data[0].name){
                            flag = true;
                        }
                    }
                    if(!flag){
                        let obj = new Object();
                        obj.name = data[0].name;
                        obj.code = data[0].code;
                        obj.amount = $('#amountAdd').val();
                        obj.sellingprice = data[0].sellingprice;
                        dataOfBasket.set(dataOfBasket.size, obj);
                        console.log(dataOfBasket);
                    }
                } else {
                    let obj = new Object();
                    obj.name = data[0].name;
                    obj.code = data[0].code;
                    obj.amount = $('#amountAdd').val();
                    obj.sellingprice = data[0].sellingprice;
                    dataOfBasket.set(dataOfBasket.size, obj);
                    console.log(dataOfBasket);
                }

                let table = document.createElement('table');
                table.id = "t";
                table.style.width = "90%";
                table.style.marginLeft = "5%";
                table.style.marginTop = "5%";
                table.style.borderSpacing = "0px";
                table.style.fontSize = "16pt";
                let html = "<tr> " +
                    "<td style='border: 1px solid #545454; border-top-left-radius:30px; text-align: center; height: 50px; background-color: #545454; color: white'>Название</td>" +
                    "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Код товара</td>"+
                    "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Количество</td>"+
                    "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена за шт</td>"+
                    "<td style='border: 1px solid #545454; border-top-right-radius: 30px; text-align: center; height: 50px; background-color: #545454; color: white'>Общая цена</td>" +
                    " </tr>";
                let summ = 0;
                let amnt = 0;
                for(let i = 0; i < dataOfBasket.size; i++){
                    summ += dataOfBasket.get(i).sellingprice * dataOfBasket.get(i).amount;
                    if(i === 0 && dataOfBasket.size === 1){
                        console.log("Этап1");
                        html += "<tr>" +
                            "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code + "</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                            "<td id='totalPrice' style='border: 1px solid #545454; border-bottom-right-radius: 30px;  text-align: center; height: 50px; background-color: white'></td>" +
                            "</tr>";
                    }
                    if(i === 0 && dataOfBasket.size !== 1){
                        console.log("Этап2");
                        html += "<tr>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code +"</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                            "<td id='totalPrice' style='border: 1px solid #545454; border-bottom-right-radius: 30px;  text-align: center; height: 50px; background-color: white'></td>" +
                            "</tr>";
                    }else if(i !== dataOfBasket.size - 1 && i !== 0){
                        console.log("Этап3");
                        html += "<tr>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code +"</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                            "</tr>";
                    } else if(i !== 0) {
                        console.log("Этап4");
                        html += "<tr>" +
                            "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code + "</td>" +
                            "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                            "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                            "</tr>";
                    }
                }
                table.innerHTML = html;
                let div = document.getElementById('databar');
                if($('table').is('#t') || $('div').is('#t')){
                    while ($('table').is('#t') || $('div').is('#t')){
                        document.getElementById('t').remove();
                    }
                }
                div.append(table);
                $('#totalPrice').text(summ);
                $('#warning').empty();
            } else {
                $('#warning').empty();
                $('#warning').prepend("На складе " + data[0].name + " осталось всего: " + data[0].amount + "!!!");
            }
        }
    })
}
function clickTranzactionDeleteProduct(){
    for(let i = 0; i < dataOfBasket.size; i++){
        $.ajax("http://localhost:8100/seller/tranzactionDeleteProductAmount", {
            type: 'post',
            data: ($.param(dataOfBasket.get(i))),
            dataType: "text",
            success: function (data){
                $('#databar').append("<div id='t' style='margin-left: 60px; font-size: 16pt'>"+data+"</div>")
            }
        })
    }
    dataOfBasket.clear();
    if($('table').is('#t')){
        document.getElementById('t').remove();
    }
}

function clickClearBasket(){
    dataOfBasket.clear();
    if($('table').is('#t')){
        document.getElementById('t').remove();
    }
}

function clickDeleteLastItemOfBasket(){
    dataOfBasket.delete(dataOfBasket.size - 1);
    let table = document.createElement('table');
    table.id = "t";
    table.style.width = "90%";
    table.style.marginLeft = "5%";
    table.style.marginTop = "5%";
    table.style.borderSpacing = "0px";
    table.style.fontSize = "16pt";
    let html = "<tr> " +
        "<td style='border: 1px solid #545454; border-top-left-radius:30px; text-align: center; height: 50px; background-color: #545454; color: white'>Название</td>" +
        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Код товара</td>"+
        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Количество</td>"+
        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена за шт</td>"+
        "<td style='border: 1px solid #545454; border-top-right-radius: 30px; text-align: center; height: 50px; background-color: #545454; color: white'>Общая цена</td>" +
        " </tr>";
    let summ = 0;
    let amnt = 0;
    for(let i = 0; i < dataOfBasket.size; i++){
        summ += dataOfBasket.get(i).sellingprice * dataOfBasket.get(i).amount;
        if(i === 0 && dataOfBasket.size === 1){
            console.log("Этап1");
            html += "<tr>" +
                "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code + "</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                "<td id='totalPrice' style='border: 1px solid #545454; border-bottom-right-radius: 30px;  text-align: center; height: 50px; background-color: white'></td>" +
                "</tr>";
        }
        if(i === 0 && dataOfBasket.size !== 1){
            console.log("Этап2");
            html += "<tr>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code +"</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                "<td id='totalPrice' style='border: 1px solid #545454; border-bottom-right-radius: 30px;  text-align: center; height: 50px; background-color: white'></td>" +
                "</tr>";
        }else if(i !== dataOfBasket.size - 1 && i !== 0){
            console.log("Этап3");
            html += "<tr>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code +"</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                "</tr>";
        } else if(i !== 0) {
            console.log("Этап4");
            html += "<tr>" +
                "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).name +"</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).code + "</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + dataOfBasket.get(i).amount + "</td>" +
                "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>"+ dataOfBasket.get(i).sellingprice + "</td>" +
                "</tr>";
        }
    }
    table.innerHTML = html;
    let div = document.getElementById('databar');
    if($('table').is('#t') || $('div').is('#t')){
        while ($('table').is('#t') || $('div').is('#t')){
            document.getElementById('t').remove();
        }
    }
    div.append(table);
    $('#totalPrice').text(summ);
}