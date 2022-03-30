import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormCreateProduct({setTableData}){
    const {register, handleSubmit} = useForm()
    const [arrayOptions, setArrayOptions] = useState(['-'])
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
    function loadOptions(){
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
    }
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Form.Control
                type='string'
                placeholder="Название"
                {...register('name')} />
        </Form.Group>
        <Form.Group>
            <Form.Control
                type='number'
                placeholder="Код"
                {...register('code')} />
        </Form.Group>
        <Form.Group>
            <Form.Control
                type='number'
                placeholder="Количество"
                {...register('amount')} />
        </Form.Group>
        <Form.Group>
            <Form.Control
                type='number'
                placeholder="Цена закупки"
                {...register('purchaseprice')} />
        </Form.Group>
        <Form.Group>
            <Form.Control
                type='number'
                placeholder="Цена продажи"
                {...register('sellingprice')} />
        </Form.Group>
        <Form.Group>
            <Form.Control
                type='string'
                placeholder="Описание"
                {...register('description')} />
        </Form.Group>
        <Form.Group>
            <Form.Select
                type='number'
                style={{border: '0px', backgroundColor: 'white',
                    margin: '3px', marginLeft:'9px'}}
                {...register('sortid')}
                onClick={loadOptions} >
                {arrayOptions.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Button variant="light" type="submit">
            Добавить продукт
        </Button>
    </Form>;
}