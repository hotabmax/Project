import React, {useState} from "react";
import {Tabs, Tab} from 'react-bootstrap'
import {FormGetHistoryOfSelling} from "./formGetHistoryOfSelling";
import {
    FormTranzactionDeleteProductAmountAndHistoryOfSelling
} from "./formTranzactionAddProductAmountAndHistoryOfSelling";
export function Displaybar({setTableData}){
    const [key, setKey] = useState('getHistoryOfSelling');
    return <Tabs
        activeKey={key}
        onSelect={(k) => setKey(k)}
    >
        <Tab eventKey="getHistoryOfSelling" title="История продаж">
            <FormGetHistoryOfSelling setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="deleteHistoryOfSelling" title="Возврат товара">
            <FormTranzactionDeleteProductAmountAndHistoryOfSelling/>
        </Tab>
    </Tabs>;
}