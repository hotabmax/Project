import React, {useState} from "react";
import {Tabs, Tab} from 'react-bootstrap'
import {FormGetStatistic} from "./formGetStatistic";

export function Displaybar(){
    const [key, setKey] = useState('getStatistic');
    return <Tabs
        activeKey={key}
        onSelect={(k) => setKey(k)}
    >
        <Tab eventKey="getStatistic" title="История продаж">
            <FormGetStatistic/>
        </Tab>
    </Tabs>;
}