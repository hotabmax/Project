import React, {useState} from "react";
import {Tabs, Tab} from 'react-bootstrap'
import {FormGetProducts} from "./formGetProducts";
import {FormCreateSort} from "./formCreateSort";
import {FormDeleteSort} from "./formDeleteSort";
import {FormCreateProduct} from "./formCreateProduct";
import {FormDeleteProduct} from "./formDeleteProduct";
export function Displaybar({setTableData}){
        const [key, setKey] = useState('getProducts');
    return <Tabs
        activeKey={key}
        onSelect={(k) => setKey(k)}
    >
        <Tab eventKey="getProducts" title="Показать продукты">
            <FormGetProducts setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="createSort" title="Добавить сорт">
            <FormCreateSort setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="deleteSort" title="Удалить сорт">
            <FormDeleteSort setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="createProduct" title="Добавить продукт">
            <FormCreateProduct setTableData={setTableData}/>
        </Tab>
        <Tab eventKey="deleteProduct" title="Удалить продукт">
            <FormDeleteProduct setTableData={setTableData}/>
        </Tab>
    </Tabs>;
}