import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@material-ui/core"
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

    useEffect(() => {
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
    }, []);

    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Логин"
                variant="outlined"
                type='string'
                {...register('name')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Пароль"
                variant="outlined"
                type='string'
                {...register('password')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Роль</InputLabel>
                    <Select
                        label="Роль"
                        {...register('roleid')}
                    >
                        {arrayOptions.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Добавить пользователя
        </Button>
    </Form>;
}