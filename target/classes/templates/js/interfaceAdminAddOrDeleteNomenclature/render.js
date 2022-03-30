import React, {useState} from "react";
import ReactDOM from "react-dom";
import {Menubar} from "../AbstractModules/menubar";
import {Displaybar} from "./displaybar";
import 'bootstrap/dist/css/bootstrap.min.css';
import {CustomTableProducts} from "./table";

export const AlertContext = React.createContext()

ReactDOM.render(
    <Menubar/>,
    document.getElementById('menubar')
)
function DisplayAndTable(){
    const [tableData, setTableData] = useState([{name: "-", code: "-", amount: "-",
        purchaseprice: "-", sellingprice: "-", description: "-"}])
    return <AlertContext.Provider value={tableData}>
        <Displaybar setTableData={setTableData}/>
        <CustomTableProducts/>
    </AlertContext.Provider>
}
ReactDOM.render(
    <DisplayAndTable/>,
    document.getElementById('displaybar')
)
/*ReactDOM.render(
    <CustomTable/>,
    document.getElementById('databar')
)*/
