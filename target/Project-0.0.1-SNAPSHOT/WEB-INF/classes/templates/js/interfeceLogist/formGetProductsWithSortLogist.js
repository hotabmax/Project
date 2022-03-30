$(document).ready(function (){
    $('#displaybar').append( "<form id='formGetProductsWithSort' style='margin: 10px;'>" +
        "   <select name='nameSort' id='selectSort' onClick='clickButtonGetProductWithSort()' style='height: 50px; font-size: 16pt; text-align: center; border: 0px; outline: none; background-color: #f8f8f8; padding-right: 5px'></select>" +
        "   </form>");

    $.ajax({
        url: "http://localhost:8100/logist/getTableSorts",
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

function clickButtonGetProductWithSort(){
    $.ajax("http://localhost:8100/logist/getTableProductsBySort",{
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
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Количество</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена закупки</td>" +
                "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: #545454; color: white'>Цена продажи</td>"+
                "<td style='border: 1px solid #545454; border-top-right-radius: 30px; text-align: center; height: 50px; background-color: #545454; color: white'>Описание</td>" +
                " </tr>";
            for (let i = 0; i < data.length; i++){
                if(i !== (data.length - 1)){
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].amount + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].purchaseprice +"</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>"+ data[i].sellingprice + "</td>" +
                        "<td style='border: 1px solid #545454; text-align: center; height: 50px; background-color: white'>" + data[i].description + "</td>" +
                        "</tr>";
                } else {
                    html += "<tr>" +
                        "<td style='border: 1px solid #545454; border-bottom-left-radius: 30px; text-align: center; height: 50px; background-color: white'>" + data[i].name +"</td>" +
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

