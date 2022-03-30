import React from "react";
import axios from "axios";
export class Select extends React.Component{
    changedOption
    selectStyle = {
        height: '50px', fontSize: '16pt', textAlign: 'center',
        outline: 'none', backgroundColor: '#f8f8f8', border: '0px', paddingRight: '5px'
    }
    render() {
        axios.post(this.props.urlForLoadSelect)
            .then(res => {
                if(res.data){
                    this.props.arrayOptions = res.data
                }
            })
        return <select name={this.props.name} value={this.changedOption} onChange={this.change} style={this.selectStyle}>
                        {this.props.arrayOptions.map(option => <option>{option}</option> )}
                </select>;
    }
    change(event){
        this.props.changedOption = event.target.value
        const request = {
            [this.props.name]: event.target.value
        }
        axios.post(this.props.urlForLoadSelect, {request})
            .then(res =>{
                this.props.stringOptions = res.data.then(this.props.updatedSelect.render())
            })
    }
}