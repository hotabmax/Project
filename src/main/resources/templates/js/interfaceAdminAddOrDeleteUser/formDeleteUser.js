import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Box, Button, FormControl, InputLabel, MenuItem, Select} from "@material-ui/core"
import axios from "axios";
export function FormDeleteUser({setTableData}){
    const {register, handleSubmit} = useForm()
    let text = "Выберете пользователя"
    const [arrayOptionsRoles, setArrayOptionsRoles] = useState(['-'])
    const [arrayOptionsUsers, setArrayOptionsUsers] = useState(['-'])
    function submit(data){
        if(data.name !== '-'){
            axios.post('http://localhost:8100/admin/deleteUser', data)
                .then(res => {
                    setTableData(res.data)
                })
        }
    }
    function loadOptions(event){
        const request = {
            name: event.target.value
        }
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
    }

    useEffect(() => {
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
    }, []);

    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Роль</InputLabel>
                    <Select
                        label="Роль"
                        onClick={loadOptions}
                    >
                        {arrayOptionsRoles.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Имя</InputLabel>
                    <Select
                        label="Имя"
                        {...register('name')}
                    >
                        {arrayOptionsUsers.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Удалить пользователя
        </Button>
    </Form>;
}