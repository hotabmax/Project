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

    $('#displaybar').remove();
    $('#databar').remove();

    $(document).ready(function (){
        $('body').append("<div id='displaybar' style='width: 90%; min-height: 400px; font-family: Arial; float: left; background-color: #f8f8f8; border-radius: 30px; margin-top: 50px; margin-left: 5%; padding-left: 50px'>" +
            "<div style='text-align: center;'><h1>Панель управления</h1></div>" +
            "</div>")
    })

    $(document).ready(function (){
        $('body').append("<div id='databar'; style='width: 90%; min-height: 300px; font-family: Arial; float: left ; background-color: #f8f8f8; border-radius: 30px; margin-top: 50px; margin-left: 5%; padding-bottom: 50px'>" +
            "<div style='text-align: center;'><h1>Товары</h1></div>" +
            "</div>")
    })

    $(document).ready(function (){
        $('#displaybar').append( "<form id='formGetProductsWithSort' style='margin: 10px;'>" +
            "   <select name='nameSort' id='selectSort' onClick='clickButtonGetProductWithSort()' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'></select>" +
            "   </form>");

        $.ajax({
            url: "http://localhost:8100/admin/getTableSorts",
            type: 'post',
            dataType: "json",
            success: function (data) {
                let sel = document.getElementById('selectSort');
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
        $('#displaybar').append("<div>" +
            "        <form id='formCreateSort' style='display: inline-block; margin: 10px;'>" +
            "        <input id='buttonAddSort' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
            "        type='button' value='Добавить группу товаров' onClick='clickCreateSort()'; onMouseOver='eventMouseOverButtonAddSort()'; onMouseOut='eventMouseOutButtonAddSort()'>" +
            "        <input name='name' placeholder='Название' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        </form>" +
            "</div>")

    })

    $(document).ready(function (){
        $('#displaybar').append("<div>" +
            "        <form id='formDeleteSort' style='display: inline-block; margin: 10px;'>" +
            "        <input id='buttonDeleteSort' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
            "        type='button' value='Удалить группу товаров' onClick='clickDeleteSort()'; onMouseOver='eventMouseOverButtonDeleteSort()'; onMouseOut='eventMouseOutButtonDeleteSort()'>" +
            "        <select name='name' id='selectDeletedSort' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
            "        </form>" +
            "</div>")

        $.ajax({
            url: "http://localhost:8100/admin/getTableSorts",
            type: 'post',
            dataType: "json",
            success: function (data) {
                let sel = document.getElementById('selectDeletedSort');
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
        $('#displaybar').append( "<div>" +
            "<form id='formCreateProduct' style='display: inline-block; margin: 10px;'>" +
            "        <input id='buttonAddProduct' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
            "        type='button' value='Добавить товар' onClick='clickCreateProduct()'; onMouseOver='eventMouseOverButtonAddProduct()'; onMouseOut='eventMouseOutButtonAddProduct()'>" +
            "        <input name='name' placeholder='Название' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <input name='code' placeholder='Код товара' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <input name='amount' placeholder='Количество' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <input name='purchaseprice' placeholder='Цена закупки' style='width: 140px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <input name='sellingprice' placeholder='Цена продажи' style='width: 150px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <input name='description' placeholder='Описание' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
            "        <select name='nameSort' id='selectSortToAddProduct' style='height: 50px; font-size: 16pt; text-align: center; outline: none; background-color: #f8f8f8; border: 0px; padding-right: 5px' ></select>" +
            "        </form>" +
            "</div>");
        $.ajax({
            url: "http://localhost:8100/admin/getTableSorts",
            type: 'post',
            dataType: "json",
            success: function (data) {
                let sel = document.getElementById('selectSortToAddProduct');
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
}

function clickButtonGetProductWithSort(){
    $.ajax("http://localhost:8100/admin/getTableProductsBySort",{
        type: 'post',
        data: $('#formGetProductsWithSort').serialize(),
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
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена продажи</td>"+
                "<td style='border: 1px solid #545454; border-top-right-radius: 30px; text-align: center; height: 50px; background-color: #545454; color: white'>Описание</td>" +
                " </tr>";
            for (let i = 0; i < data.length; i++){
                if(i !== (data.length - 1)){
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
                        "</tr>";
                } else {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
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

function eventMouseOverButtonAddSort(){
    $('#buttonAddSort').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonAddSort(){
    $('#buttonAddSort').css('backgroundColor', '#f8f8f8');
}

function clickCreateSort(){
    $.ajax("http://localhost:8100/admin/createSort",{
        type: 'post',
        data: $('#formCreateSort').serialize(),
    })
    setTimeout(function (){
        $.ajax({
            url: "http://localhost:8100/admin/getTableSorts",
            type: 'post',
            dataType: "json",
            success: function (data) {
                console.log(data);
                let sel1 = document.getElementById('selectSort');
                while (sel1.firstChild) {
                    sel1.removeChild(sel1.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel1.prepend(opt);
                }

                let sel2 = document.getElementById('selectDeletedSort');
                while (sel2.firstChild) {
                    sel2.removeChild(sel2.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel2.prepend(opt);
                }

                let sel3 = document.getElementById('selectSortToAddProduct');
                while (sel3.firstChild) {
                    sel3.removeChild(sel3.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel3.prepend(opt);
                }

                let sel4 = document.getElementById('selectSortToDeleteProduct');
                while (sel4.firstChild) {
                    sel4.removeChild(sel4.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel4.prepend(opt);
                }

                let sel5 = document.getElementById('selectSortToFindTAProduct');
                while (sel5.firstChild) {
                    sel5.removeChild(sel5.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel5.prepend(opt);
                }

                let sel6 = document.getElementById('selectSortToFindTDProduct');
                while (sel6.firstChild) {
                    sel6.removeChild(sel6.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel6.prepend(opt);
                }
            }
        })
    },100)
}

function eventMouseOverButtonDeleteSort(){
    $('#buttonDeleteSort').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonDeleteSort(){
    $('#buttonDeleteSort').css('backgroundColor', '#f8f8f8');
}

function clickDeleteSort(){
    $.ajax("http://localhost:8100/admin/deleteSort",{
        type: 'post',
        data: $('#formDeleteSort').serialize(),
    })

    setTimeout(function (){
        $.ajax({
            url: "http://localhost:8100/admin/getTableSorts",
            type: 'post',
            dataType: "json",
            success: function (data) {
                console.log(data);
                let sel1 = document.getElementById('selectSort');
                while (sel1.firstChild) {
                    sel1.removeChild(sel1.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel1.prepend(opt);
                }

                let sel2 = document.getElementById('selectDeletedSort');
                while (sel2.firstChild) {
                    sel2.removeChild(sel2.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel2.prepend(opt);
                }

                let sel3 = document.getElementById('selectSortToAddProduct');
                while (sel3.firstChild) {
                    sel3.removeChild(sel3.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel3.prepend(opt);
                }

                let sel4 = document.getElementById('selectSortToDeleteProduct');
                while (sel4.firstChild) {
                    sel4.removeChild(sel4.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel4.prepend(opt);
                }

                let sel5 = document.getElementById('selectSortToFindTAProduct');
                while (sel5.firstChild) {
                    sel5.removeChild(sel5.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel5.prepend(opt);
                }

                let sel6 = document.getElementById('selectSortToFindTDProduct');
                while (sel6.firstChild) {
                    sel6.removeChild(sel6.firstChild);
                }
                for (let i = 0; i < data.length; i++){
                    let name = data[i].name;
                    let opt = document.createElement('option');
                    opt.innerHTML = name;
                    sel6.prepend(opt);
                }
            }
        })
    },100)
}

function eventMouseOverButtonAddProduct(){
    $('#buttonAddProduct').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonAddProduct(){
    $('#buttonAddProduct').css('backgroundColor', '#f8f8f8');
}

function clickCreateProduct(){
    $.ajax("http://localhost:8100/admin/createProduct",{
        type: 'post',
        data: $('#formCreateProduct').serialize(),
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
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена продажи</td>"+
                "<td style='border: 1px solid #545454; border-top-right-radius: 30px; text-align: center; height: 50px; background-color: #545454; color: white'>Описание</td>" +
                " </tr>";
            for (let i = 0; i < data.length; i++){
                if(i !== (data.length - 1)){
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
                        "</tr>";
                } else {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; border-bottom-right-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
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
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена продажи</td>"+
                "<td style='border: 1px solid #545454; border-top-right-radius: 30px; text-align: center; height: 50px; background-color: #545454; color: white'>Описание</td>" +
                " </tr>";
            for (let i = 0; i < data.length; i++){
                if(i !== (data.length - 1)){
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
                        "</tr>";
                } else {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].code + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ data[i].sellingprice + "</td>" +
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
}