import React, {useState} from "react";
export function ButtonRedirectNomenclature(){
    const [buttonStyle, setButtonStyle] = useState({
        width: '200px', height: '50px', fontSize: '16pt',
        float: 'left', border: '0px', borderRadius: '10px',
        backgroundColor: 'white', marginRight: '25px', marginTop: '25px'
    })
    function eventMouseOver(){
        setButtonStyle({ width: '200px', height: '50px', fontSize: '16pt',
            float: 'left', border: '0px', borderRadius: '10px',
            backgroundColor: '#EEEEEF', marginRight: '25px', marginTop: '25px' })
    }
    function eventMouseOut(){
        setButtonStyle({ width: '200px', height: '50px', fontSize: '16pt',
            float: 'left', border: '0px', borderRadius: '10px',
            backgroundColor: 'white', marginRight: '25px', marginTop: '25px' })
    }
    function click(){
        document.body.fadeOut(200, function () {
            document.location.href = 'http://localhost:8100/adminAddOrDeleteNomenclature'
        }).fadeIn(200);
    }
    return <input type='button' value='Номенклатура' style={buttonStyle}
                  onClick={click} onMouseOver={eventMouseOver}
                  onMouseOut={eventMouseOut}/>;
}