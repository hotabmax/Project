import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Button} from "@material-ui/core"
import axios from "axios";
import {Box, FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
export function FormDeleteProduct({setTableData}){
    let boolean;
    const {register, handleSubmit} = useForm()
    let text = "Выберете продукцию"
    const [arrayOptionsSort, setArrayOptionsSort] = useState([' '])
    const [arrayOptionsProduct, setArrayOptionsProduct] = useState([' '])
    function submit(data){
        if(data.name !== '-'){
            axios.post('http://localhost:8100/admin/deleteProduct', data)
                .then(res => {
                    setTableData(res.data)
                })
        }
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
        <Box>
            <FormControl variant="outlined" style={{marginTop:'10px',minWidth: '150px'}}>
                <InputLabel>Продукт</InputLabel>
                <Select
                    label="Продукт"
                    {...register('name')}
                >
                    {arrayOptionsProduct.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                </Select>
            </FormControl>
        </Box>
        <Button style={{ marginTop:'10px'}} variant="outlined" type="submit">
            Удалить продукт
        </Button>
    </Form>;
}