import React, {useContext} from "react";
import {AlertContext} from "./render";
import {Table} from "react-bootstrap";

export function CustomTableUsers(){
    const tableData = useContext(AlertContext)

    return <Table striped bordered>
        <thead>
        <tr>
            <th>Логин</th>
            <th>Пароль</th>
        </tr>
        </thead>
        <tbody>
        {tableData.map((record) => <tr>
            <td>{record.name}</td>
            <td>{record.password}</td>
        </tr>)}
        </tbody>
    </Table>
}