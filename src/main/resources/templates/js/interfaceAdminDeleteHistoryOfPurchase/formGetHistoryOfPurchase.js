import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormGetHistoryOfPurchase({setTableData}){
    const {register, handleSubmit} = useForm()
    var arrayOptionsYears = ['2022', '2023', '2024', '2025', '2026', '2027', '2028', '2029', '2030',
        '2031', '2032', '2033', '2034', '2035', '2036', '2037', '2038', '2039', '2040']
    var arrayOptionsMonths = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
    var arrayOptionsDays = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11',
        '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26',
        '27', '28', '29', '30', '31']
    function submit(data){
        data.date = data.year + '.' + data.month + '.' + data.day
        console.log(data)
        axios.post('http://localhost:8100/admin/getHistoryOfPurchase', data)
            .then(res => {
                setTableData(res.data)
            })
    }
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Form.Label style={{margin: '3px', marginLeft:'9px'}}>
                Год
            </Form.Label>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white'}}
                {...register('year')}>
                {arrayOptionsYears.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Form.Group>
            <Form.Label style={{margin: '3px', marginLeft:'9px'}}>
                Месяц
            </Form.Label>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white'}}
                {...register('month')}>
                {arrayOptionsMonths.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Form.Group>
            <Form.Label style={{margin: '3px', marginLeft:'9px'}}>
                День
            </Form.Label>
            <Form.Select
                style={{border: '0px', backgroundColor: 'white'}}
                {...register('day')}>
                {arrayOptionsDays.map((option) => <option>{option}</option>)}
            </Form.Select>
        </Form.Group>
        <Button variant="light" type="submit">
            Показать закупки
        </Button>
    </Form>;
}