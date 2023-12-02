import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@material-ui/core"
import axios from "axios";
export function FormTranzactionDeleteProductAmountAndHistoryOfSelling(){
    const {register, handleSubmit} = useForm()
    const [arrayOptionsSort, setArrayOptionsSort] = useState([' '])
    const [arrayOptionsProduct, setArrayOptionsProduct] = useState([' '])
    let text1 = "Выберите продукцию"
    const [textWarningSelect, setTextWarningSelect] = useState()
    function submit(data){
            if(data.name !== '-'){
                setTextWarningSelect()
                axios.post('http://localhost:8100/admin/tranzactionDeleteHistoryOfSelling', data)
            } else setTextWarningSelect(text1)
    }
    function loadOptions(event){
        const request = {
            name: event.target.value
        }
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
    }

    useEffect(() => {
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
    }, []);

    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Вид продукта</InputLabel>
                    <Select
                        label="Вид продукта"
                        onClick={loadOptions}
                    >
                        {arrayOptionsSort.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Продукт</InputLabel>
                    <Select
                        label="Продукт"
                        {...register('name')}
                    >
                        {arrayOptionsProduct.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Количество"
                variant="outlined"
                type='string'
                {...register('amount')}
            ></TextField>
            <Form.Label>
                {textWarningSelect}
            </Form.Label>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Вернуть товар на склад
        </Button>
    </Form>;
}