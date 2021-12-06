$(document).ready(function (){
    $('#displaybar').append( "<div>" +
        "<form id='formDeleteProduct' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonDeleteProduct' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
        "        type='button' value='Удалить товар' onClick='clickDeleteProduct()'; onMouseOver='eventMouseOverButtonDeleteProduct()'; onMouseOut='eventMouseOutButtonDeleteProduct()'>" +
        "        <select name='nameSort' id='selectSortToDeleteProduct' onClick='selectProductsForDelete()' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
        "        <select name='name' id='selectDeletedProduct' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
        "        </form>" +
        "</div>");
    $.ajax({
        url: "http://localhost:8100/admin/getTableSorts",
        type: 'post',
        dataType: "json",
        success: function (data) {
            let sel = document.getElementById('selectSortToDeleteProduct');
            for (let i = 0; i < data.length; i++){
                let name = data[i].name;
                let opt = document.createElement('option');
                opt.innerHTML = name;
                sel.prepend(opt);
            }
        }
    })
})

function eventMouseOverButtonDeleteProduct(){
    $('#buttonDeleteProduct').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonDeleteProduct(){
    $('#buttonDeleteProduct').css('backgroundColor', '#f8f8f8');
}

function selectProductsForDelete() {
    $.ajax({
        url: "http://localhost:8100/admin/getTableProductsBySort",
        type: 'post',
        data: $('#formDeleteProduct').serialize(),
        dataType: "json",
        success: function (data) {
            let sel = document.getElementById('selectDeletedProduct');
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
function clickDeleteProduct() {
    $.ajax("http://localhost:8100/admin/deleteProduct", {
        type: 'post',
        data: $('#formDeleteProduct').serialize(),
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
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Код товара</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Количество</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена закупки</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена продажи</td>" +
                "<td style='border: 1px solid #545454; border-top-right-radius: 30px; text-align: center; height: 50px; background-color: #545454; color: white'>Описание</td>" +
                " </tr>";
            for (let i = 0; i < data.length; i++) {
                if (i !== (data.length - 1)) {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
                        "</tr>";
                } else {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
                        "</tr>";
                }

            }
            table.innerHTML = html;
            let div = document.getElementById('databar');
            if ($('table').is('#t')) {
                document.getElementById('t').remove();
            }
            div.append(table);
        }
    })
    setTimeout(function (){
    $.ajax({
        url: "http://localhost:8100/admin/getTableProductsBySort",
        type: 'post',
        data: $('#formDeleteProduct').serialize(),
        dataType: "json",
        success: function (data) {
            let sel = document.getElementById('selectDeletedProduct');
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
    },100)
}