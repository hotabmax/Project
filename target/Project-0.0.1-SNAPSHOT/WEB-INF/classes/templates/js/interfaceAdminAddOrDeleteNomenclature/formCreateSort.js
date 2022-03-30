import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormCreateSort(){
    const {register, handleSubmit} = useForm()
    function submit(data){
        axios.post('http://localhost:8100/admin/createSort', data)
    }
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Form.Control
                type="text"
                placeholder="Имя сорта"
                {...register('name')} />
        </Form.Group>
        <Button variant="light" type="submit">
            Добавить сорт
        </Button>
    </Form>;
}