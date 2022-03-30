import React, {useState} from "react";
import {Tabs, Tab} from 'react-bootstrap'
import {FormGetHistoryOfPurchase} from "./formGetHistoryOfPurchase";
import {
    FormTranzactionDeleteProductAmountAndHistoryOfPurchase
} from "./formTranzactionDeleteProductAmountAndHistoryOfPurchase";
export function Displaybar({setTableData}){
        const [key, setKey] = useState('getHistoryOfPurchase');
    return <Tabs
        activeKey={key}
        onSelect={(k) => setKey(k)}
    >
        <Tab eventKey="getHistoryOfPurchase" title="История закупок">
            <FormGetHistoryOfPurchase setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="deleteHistoryOfPurchase" title="Списание товара">
            <FormTranzactionDeleteProductAmountAndHistoryOfPurchase/>
        </Tab>
    </Tabs>;
}