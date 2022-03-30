import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormAddUser({setTableData}){
    const {register, handleSubmit} = useForm()
    const [arrayOptions, setArrayOptions] = useState(['-'])
    function submit(data){
        axios.post('http://localhost:8100/admin/getTableRoles')
            .then(res=>{
                for(let i = 0; i < res.data.length;i++){
                    if(data.roleid === res.data[i].name){
                        data.roleid = res.data[i].id
                    }
                }
                axios.post('http://localhost:8100/admin/createUser', data)
                    .then(res => {
                        setTableData(res.data)
                    })
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
            <Form.Control
                type="text"
                placeholder="Логин"
                {...register('name')} />
        </Form.Group>
        <Form.Group>
            <Form.Control
                type="text"
                placeholder="Пароль"
                {...register('password')} />
        </Form.Group>
        <Form.Group>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white',
                    margin: '3px', marginLeft:'9px'}}
                {...register('roleid')}
                onClick={loadOptions}>
                {arrayOptions.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Button variant="light" type="submit">
            Добавить пользователя
        </Button>
    </Form>;
}