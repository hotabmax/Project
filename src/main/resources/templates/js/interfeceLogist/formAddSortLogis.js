$(document).ready(function (){
    $('#displaybar').append("<div>" +
        "        <form id='formCreateSort' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonAddSort' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
        "        type='button' value='Добавить группу товаров' onClick='clickCreateSort()'; onMouseOver='eventMouseOverButtonAddSort()'; onMouseOut='eventMouseOutButtonAddSort()'>" +
        "        <input name='name' placeholder='Название' style='width: 120px; height: 45px; font-size: 16pt; transition: 1000s; outline: none; background-color: #f8f8f8; border-bottom-width: 1px; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; text-align: center'>" +
        "        </form>" +
        "</div>")

})

function eventMouseOverButtonAddSort(){
    $('#buttonAddSort').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonAddSort(){
    $('#buttonAddSort').css('backgroundColor', '#f8f8f8');
}

function clickCreateSort(){
    $.ajax("http://vsenaydetsia.ml/logist/createSort",{
        type: 'post',
        data: $('#formCreateSort').serialize(),
    })
    setTimeout(function (){
        $.ajax({
            url: "http://vsenaydetsia.ml/logist/getTableSorts",
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