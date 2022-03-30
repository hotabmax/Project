import React, {useContext} from "react";
import {AlertContext} from "./render";
import {Table} from "react-bootstrap";

export function CustomTableHistoryOfSelling(){
    const tableData = useContext(AlertContext)

    return <Table striped bordered>
        <thead>
        <tr>
            <th>Название</th>
            <th>Количество</th>
            <th>Дата</th>
            <th>Время</th>
            <th>Продавец</th>
        </tr>
        </thead>
        <tbody>
        {tableData.map((record) => <tr>
            <td>{record.name}</td>
            <td>{record.amount}</td>
            <td>{record.date}</td>
            <td>{record.time}</td>
            <td>{record.sellername}</td>
        </tr>)}
        </tbody>
    </Table>
}