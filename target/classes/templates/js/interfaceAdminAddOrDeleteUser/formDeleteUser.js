import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormDeleteUser({setTableData}){
    const {register, handleSubmit} = useForm()
    let text = "Выберете пользователя"
    const [textWarning, setTextWarning] = useState()
    const [arrayOptionsRoles, setArrayOptionsRoles] = useState(['-'])
    const [arrayOptionsUsers, setArrayOptionsUsers] = useState(['-'])
    function submit(data){
        if(data.name !== '-'){
            setTextWarning()
            axios.post('http://localhost:8100/admin/deleteUser', data)
                .then(res => {
                    setTableData(res.data)
                })
        } else setTextWarning(text)
    }
    function loadOptions(event){
        const request = {
            name: event.target.value
        }
        axios.post('http://localhost:8100/admin/getTableRoles')
            .then(res => {
                if (res.data){
                    let arrayNames = [];
                    for(let i = 0; i < res.data.length; i++){
                        arrayNames[i] = res.data[i].name
                    }
                    setArrayOptionsRoles(arrayNames)
                }
            })
            .then(
                axios.post('http://localhost:8100/admin/getTableUsersByRole', request)
                    .then(res => {
                        if (res.data){
                            let arrayNames = [];
                            for(let i = 0; i < res.data.length; i++){
                                arrayNames[i] = res.data[i].name
                            }
                            setArrayOptionsUsers(arrayNames)
                        }
                    })
            )
    }
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white',
                    margin: '3px', marginLeft:'9px'}}
                onClick={loadOptions}>
                {arrayOptionsRoles.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Form.Group>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white',
                    margin: '3px', marginLeft:'9px'}}
                {...register('name')}>
                {arrayOptionsUsers.map((option) => <option>{option}</option>)}
            </Form.Select>
            <Form.Label>
                {textWarning}
            </Form.Label>
        </Form.Group>
        <Button variant="light" type="submit">
            Удалить пользователя
        </Button>
    </Form>;
}