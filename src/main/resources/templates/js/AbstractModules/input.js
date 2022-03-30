import React from "react";
export class Input extends React.Component{
    valueInput
    inputStyle = {
        height: '45px', fontSize: '16pt', transition: '1000s', outline: 'none',
        backgroundColor: '#f8f8f8', borderBottomWidth: '1px', borderTopWidth: '0px',
        borderRightWidth: '0px', borderLeftWidth: '0px', textAlign: 'center'
    }
    render() {
        this.inputStyle.width = this.props.width
        return <input name={this.props.name} value={this.valueInput} placeholder={this.props.placeholder} style={this.inputStyle}/>;
    }
}