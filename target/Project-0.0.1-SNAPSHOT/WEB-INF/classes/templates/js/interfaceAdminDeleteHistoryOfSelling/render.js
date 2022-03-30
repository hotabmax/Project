import React, {useState} from "react";
import ReactDOM from "react-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Menubar} from "../AbstractModules/menubar";
import {Displaybar} from "./displaybar";
import {CustomTableHistoryOfSelling} from "./table";

export const AlertContext = React.createContext()

ReactDOM.render(
    <Menubar/>,
    document.getElementById('menubar')
)
function DisplayAndTable(){
    const [tableData, setTableData] = useState([{name: "-", amount: "-", date: "-",
        time: "-", sellername: "-"}])
    return <AlertContext.Provider value={tableData}>
        <Displaybar setTableData={setTableData}/>
        <CustomTableHistoryOfSelling/>
    </AlertContext.Provider>
}
ReactDOM.render(
    <DisplayAndTable/>,
    document.getElementById('displaybar')
)