import React, {useState} from "react";
import ReactDOM from "react-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Menubar} from "../AbstractModules/menubar";
import {Displaybar} from "./displaybar";
import {CustomTableUsers} from "./table";

export const AlertContext = React.createContext()

ReactDOM.render(
    <Menubar/>,
    document.getElementById('menubar')
)
function DisplayAndTable(){
    const [tableData, setTableData] = useState([{name: "-", password: "-"}])
    return <AlertContext.Provider value={tableData}>
        <Displaybar setTableData={setTableData}/>
        <CustomTableUsers/>
    </AlertContext.Provider>
}
ReactDOM.render(
    <DisplayAndTable/>,
    document.getElementById('displaybar')
)