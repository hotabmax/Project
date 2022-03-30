import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormDeleteProduct({setTableData}){
    let boolean;
    const {register, handleSubmit} = useForm()
    let text = "Выберете продукцию"
    const [textWarning, setTextWarning] = useState()
    const [arrayOptionsSort, setArrayOptionsSort] = useState(['-'])
    const [arrayOptionsProduct, setArrayOptionsProduct] = useState(['-'])
    function submit(data){
        if(data.name !== '-'){
            setTextWarning()
            axios.post('http://localhost:8100/admin/deleteProduct', data)
                .then(res => {
                    setTableData(res.data)
                })
        } else setTextWarning(text)
    }
    function loadOptions(event){
        const request = {
            name: event.target.value
        }
        axios.post('http://localhost:8100/admin/getTableSorts')
            .then(res => {
                if (res.data){
                    let arrayNames = [];
                    for(let i = 0; i < res.data.length; i++){
                        arrayNames[i] = res.data[i].name
                    }
                    setArrayOptionsSort(arrayNames)
                }
            })
            .then(
                axios.post('http://localhost:8100/admin/getTableProductsBySort', request)
                    .then(res => {
                        if (res.data){
                            let arrayNames = [];
                            for(let i = 0; i < res.data.length; i++){
                                arrayNames[i] = res.data[i].name
                            }
                            setArrayOptionsProduct(arrayNames)
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
                {arrayOptionsSort.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Form.Group>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white',
                    margin: '3px', marginLeft:'9px'}}
                {...register('name')}>
                {arrayOptionsProduct.map((option) => <option>{option}</option>)}
            </Form.Select>
            <Form.Label>
                {textWarning}
            </Form.Label>
        </Form.Group>
        <Button variant="light" type="submit">
            Удалить продукт
        </Button>
    </Form>;
}