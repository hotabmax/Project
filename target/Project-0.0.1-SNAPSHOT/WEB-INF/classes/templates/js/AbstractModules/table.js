import React from "react";
import {tableStyle, tdMidleStyle, tdTopMidleStyle,
    tdTopLeftStyle, tdTopRightStyle, tdDownRightStyle,
    tdDownLeftStyle} from "./styles/stylesForTable";

export class Select extends React.Component{
    render() {
        let row = "";
        let rows = "";
        for (let name in props.names){
            if(name === props.names[0]){
                row += <td style={tdTopLeftStyle}>{name}</td>
            } else if(name === props.names[this.props.names.length - 1]){
                <td style={tdTopRightStyle}>{name}</td>
            } else{
                <td style={tdTopMidleStyle}>{name}</td>
            }
        }
        for (let i = 0; i < props.data.length; i++) {
            if (i !== (props.data.length - 1)) {
                let temporary = ""
                for (let variable in this.props.data[i]){
                    temporary += <td style={this.tdMidleStyle}>{this.props.data[i][variable]}</td>
                }
                rows += <tr>{temporary}</tr>
            }else {
                let temporary = ""
                    for (let variable in this.props.data[i]){
                        if(variable === Object.keys(this.props.data[i])[0]){
                            temporary += <td style={this.tdDownLeftStyle}>{this.props.data[i][variable]}</td>
                        } else if (variable === Object.keys(this.props.data[i])[Object.keys(this.props.data[i]).length - 1]){
                            temporary += <td style={this.tdDownRightStyle}>{this.props.data[i][variable]}</td>
                        } else {
                            temporary += <td style={this.tdMidleStyle}>{this.props.data[i][variable]}</td>
                        }
                    }
                rows += <tr>{temporary}</tr>
            }
        }
        return <table style={this.tableStyle}>
                    {row}
                    {rows}
                </table>;
    }
}