import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@material-ui/core"
import axios from "axios";
export function FormCreateProduct({setTableData}){
    const {register, handleSubmit} = useForm()
    const [arrayOptions, setArrayOptions] = useState([' '])
    function submit(data){
        axios.post('http://localhost:8100/admin/getTableSorts')
            .then(res=>{
                console.log(data)
                for(let i = 0; i < res.data.length;i++){
                    if(data.sortid === res.data[i].name){
                        data.sortid = res.data[i].id
                    }
                }
                axios.post('http://localhost:8100/admin/createProduct', data)
                    .then(res => {
                        setTableData(res.data)
                    })
            })
    }

    useEffect(() => {
        axios.post('http://localhost:8100/admin/getTableSorts')
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
                label="Название"
                variant="outlined"
                type='string'
                {...register('name')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Код"
                variant="outlined"
                type='number'
                {...register('code')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Количество"
                variant="outlined"
                type='number'
                {...register('amount')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Цена закупки"
                variant="outlined"
                type='number'
                {...register('purchaseprice')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Цена продажи"
                variant="outlined"
                type='number'
                {...register('sellingprice')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Описание"
                variant="outlined"
                type='string'
                {...register('description')}
            ></TextField>
        </Form.Group>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Вид продукта</InputLabel>
                    <Select
                        label="Вид продукта"
                        {...register('sortid')}
                    >
                        {arrayOptions.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Добавить продукт
        </Button>
    </Form>;
}