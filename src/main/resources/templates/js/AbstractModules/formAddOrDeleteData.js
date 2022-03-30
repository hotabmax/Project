import React from "react";
import axios from "axios";
export class FormAddOrDeleteData extends React.Component{
    constructor() {
        super();
        this.eventClickButton = this.eventClickButton.bind(this);
        this.updateSelectsBeforeUpdateDatabase = this.updateSelectsBeforeUpdateDatabase.bind(this);
    }
    formStyle = {
        display: 'inline-block', margin: '10px'
    }
    buttonStyle = {
        height: '50px', fontSize: '16pt', border: '0px', borderRadius: '10px',
        backgroundColor: '#f8f8f8', paddingRight: '5px'
    }
    render(){
        return <div>
            <form style={this.formStyle}>
                <input style={this.buttonStyle} type='button' value={this.props.valueButtonSubmit}
                onMouseOver={this.eventMouseOver}
                onMouseOut={this.eventMouseOut}
                onClick={this.eventClickButton}
                onClick={this.updateSelectsBeforeUpdateDatabase}/>
                    {this.props.selects.map(select => {select})}
                    {this.props.inputs.map(input => {input})}
            </form>
            </div>;

    };
    eventMouseOver(){
        this.css('backgroundColor', '#EEEEEF');
    };
    eventMouseOut(){
        this.css('backgroundColor', '#f8f8f8');
    };
    eventClickButton(){
        let request
        for (let select in this.props.selects){
            request[select.props.name] = select.state.changedOption
        }
        for (let input in this.props.inputs){
            request[input.props.name] = input.state.valueInput
        }
        axios.post(this.props.requestUrlForUpdateDatabase, {request})
            .then(res => {
                this.props.table.props.data = res.data.then(this.props.table.render)
            })
    }
    updateSelectsBeforeUpdateDatabase(){
        var array = this.props.arrayObjectsOfSelectIdAndRequestUrl
        if (array !== undefined){
            setTimeout(function (){
                for(let i = 0; i < array.length; i++){
                    axios.post(array[i].url)
                        .then(res => {
                            array[i].select.props.arrayOptions
                                = res.data.then(this.props.arrayObjectsOfSelectIdAndRequestUrl.render())
                        })
                }
            }, 100)
        }

    }
}