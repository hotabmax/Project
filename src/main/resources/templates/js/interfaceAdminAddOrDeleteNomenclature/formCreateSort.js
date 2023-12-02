import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Button} from "@material-ui/core"
import {TextField} from "@material-ui/core"
import axios from "axios";
export function FormCreateSort(){
    const {register, handleSubmit} = useForm()
    function submit(data){
        axios.post('http://localhost:8100/admin/createSort', data)
    }
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <TextField
                style={{marginTop:'10px'}}
                label="Вид продукта"
                variant="outlined"
                {...register('name')}
            ></TextField>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Добавить сорт
        </Button>
    </Form>;
}