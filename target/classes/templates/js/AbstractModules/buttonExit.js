import React, {useState} from "react";
export function ButtonExit(){
    const [buttonStyle, setButtonStyle] = useState({
        width: '200px', height: '50px', fontSize: '16pt',
        float: 'right', border: '0px', borderRadius: '10px',
        backgroundColor: 'white', marginRight: '25px', marginTop: '25px'
    })
    function eventMouseOver(){
        setButtonStyle({ width: '200px', height: '50px', fontSize: '16pt',
            float: 'right', border: '0px', borderRadius: '10px',
            backgroundColor: '#EEEEEF', marginRight: '25px', marginTop: '25px' })
    }
    function eventMouseOut(){
        setButtonStyle({ width: '200px', height: '50px', fontSize: '16pt',
            float: 'right', border: '0px', borderRadius: '10px',
            backgroundColor: 'white', marginRight: '25px', marginTop: '25px' })
    }
    function click(){
        $.ajax({
            url: "http://localhost:8100/exit",
            type: 'get',
            success: function () {
                location.reload()
            }
        })
    }
    return <input type='button' value='Выход' style={buttonStyle}
                  onClick={click} onMouseOver={eventMouseOver}
                  onMouseOut={eventMouseOut}/>;
}