import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField} from "@material-ui/core"
import axios from "axios";
export function FormTranzactionDeleteProductAmountAndHistoryOfPurchase(){
    const {register, handleSubmit} = useForm()
    const [arrayOptionsSort, setArrayOptionsSort] = useState([' '])
    const [arrayOptionsProduct, setArrayOptionsProduct] = useState([' '])
    let text = "На складе осталось всего:"
    const [textWarningAmount, setTextWarningAmount] = useState()
    let text1 = "Выберите продукцию"
    const [textWarningSelect, setTextWarningSelect] = useState()
    const [amountProduct, setAmountProduct] = useState()
    function submit(data){
        axios.post('http://localhost:8100/admin/getTableProducts', data)
            .then(res => {
                if(data.name !== '-'){
                    setTextWarningSelect()
                    if (res.data[0].amount >= data.amount){
                        axios.post('http://localhost:8100/admin/tranzactionDeleteHistoryOfPurchase', data)
                        setAmountProduct()
                        setTextWarningAmount()
                    } else {
                        setAmountProduct(res.data[0].amount)
                        setTextWarningAmount(text)
                    }
                } else setTextWarningSelect(text1)
            })

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
                    <InputLabel>Вид товара</InputLabel>
                    <Select
                        label="Вид товара"
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
                    <InputLabel>Вид товара</InputLabel>
                    <Select
                        label="Вид товара"
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
                {textWarningAmount} {amountProduct}
            </Form.Label>
            <Form.Label>
                {textWarningSelect}
            </Form.Label>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Списать товар со склада
        </Button>
    </Form>;
}