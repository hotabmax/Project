$(document).ready(function (){
    $('#displaybar').append("<div>" +
        "        <form id='formDeleteSort' style='display: inline-block; margin: 10px;'>" +
        "        <input id='buttonDeleteSort' style=' height: 50px; font-size: 16pt; border: 0px; border-radius:10px; background-color: #f8f8f8; padding-right: 5px'\n" +
        "        type='button' value='Удалить группу товаров' onClick='clickDeleteSort()'; onMouseOver='eventMouseOverButtonDeleteSort()'; onMouseOut='eventMouseOutButtonDeleteSort()'>" +
        "        <select name='name' id='selectDeletedSort' style='height: 50px; font-size: 16pt; text-align: center; outline: none; border:0px; background-color: #f8f8f8; padding-right: 5px' ></select>" +
        "        </form>" +
        "</div>")

    $.ajax({
        url: "http://localhost:8100/logist/getTableSorts",
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

function eventMouseOverButtonDeleteSort(){
    $('#buttonDeleteSort').css('backgroundColor', '#EEEEEF');
}
function eventMouseOutButtonDeleteSort(){
    $('#buttonDeleteSort').css('backgroundColor', '#f8f8f8');
}

function clickDeleteSort(){
    $.ajax("http://localhost:8100/logist/deleteSort",{
        type: 'post',
        data: $('#formDeleteSort').serialize(),
    })

    setTimeout(function (){
        $.ajax({
            url: "http://localhost:8100/logist/getTableSorts",
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