import React, {useContext, useEffect, useRef, useState} from "react";
import {Table} from "react-bootstrap";
import {AlertContext} from "./render";
export function CustomTableProducts(){
    const tableData = useContext(AlertContext)

    return <Table style={{marginTop:'10px', width: '96%'}} striped bordered>
        <thead>
        <tr>
            <th>Название</th>
            <th>Код</th>
            <th>Количество</th>
            <th>Цена закупки</th>
            <th>Цена продажи</th>
            <th>Описание</th>
        </tr>
        </thead>
        <tbody>
        {tableData.map((record) => <tr>
            <td>{record.name}</td>
            <td>{record.code}</td>
            <td>{record.amount}</td>
            <td>{record.purchaseprice}</td>
            <td>{record.sellingprice}</td>
            <td>{record.description}</td>
        </tr>)}
        </tbody>
    </Table>
}