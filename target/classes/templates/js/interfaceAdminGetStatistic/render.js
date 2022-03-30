import React, {useState} from "react";
import ReactDOM from "react-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Menubar} from "../AbstractModules/menubar";
import {Displaybar} from "./displaybar";


ReactDOM.render(
    <Menubar/>,
    document.getElementById('menubar')
)
ReactDOM.render(
    <Displaybar/>,
    document.getElementById('displaybar')
)