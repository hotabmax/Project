import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormGetUsers({setTableData}){
    const {register, handleSubmit} = useForm()
    const [arrayOptions, setArrayOptions] = useState(['-'])
    function submit(data){
        axios.post('http://localhost:8100/admin/getTableUsersByRole', data)
            .then(res => {
                setTableData(res.data)
            })
    }
    function loadOptions(){
        axios.post('http://localhost:8100/admin/getTableRoles')
            .then(res => {
                if (res.data){
                    let arrayNames = [];
                    for(let i = 0; i < res.data.length; i++){
                        arrayNames[i] = res.data[i].name
                    }
                    setArrayOptions(arrayNames)
                }
            })
    }
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white',
                    margin: '3px', marginLeft:'9px'}}
                {...register('name')}
                onClick={loadOptions}>
                {arrayOptions.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Button variant="light" type="submit">
            Показать пользователей
        </Button>
    </Form>;
}