import React, {useState} from "react";
import {Tabs, Tab} from 'react-bootstrap'
import {FormGetUsers, FromGetUsers} from "./fromGetUsers";
import {FormAddUser} from "./formAddUser";
import {FormDeleteUser} from "./formDeleteUser";
export function Displaybar({setTableData}){
        const [key, setKey] = useState('getUser');
    return <Tabs
        activeKey={key}
        onSelect={(k) => setKey(k)}
    >
        <Tab eventKey="getUser" title="Показать пользователей">
            <FormGetUsers setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="createUser" title="Добавить пользователя">
            <FormAddUser setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="deleteUser" title="Удалить пользователя">
            <FormDeleteUser setTableData={setTableData}/>
        </Tab>
    </Tabs>;
}