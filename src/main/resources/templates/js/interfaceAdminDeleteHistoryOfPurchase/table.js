import React, {useContext} from "react";
import {Table, TableBody, TableCell, TableHead, TableRow} from '@material-ui/core';
import {AlertContext} from "./render";
import {TableContainer} from "@material-ui/core";
import {Paper} from "@material-ui/core";

export function CustomTableHistoryOfPurchase(){
    const tableData = useContext(AlertContext)
    return (
        <TableContainer style={{marginTop:'10px'}} component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Название</TableCell>
                        <TableCell>Количество</TableCell>
                        <TableCell>Дата</TableCell>
                        <TableCell>Время</TableCell>
                        <TableCell>Логист</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {tableData.map((record) =>
                        <TableRow>
                            <TableCell>{record.name}</TableCell>
                            <TableCell>{record.amount}</TableCell>
                            <TableCell>{record.date}</TableCell>
                            <TableCell>{record.time}</TableCell>
                            <TableCell>{record.logistname}</TableCell>
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        </TableContainer>
    )
}