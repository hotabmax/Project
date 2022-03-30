import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form, Button} from "react-bootstrap"
import axios from "axios";
export function FormGetStatistic(){
    const {register, handleSubmit} = useForm()
    const [textResult, setTextResult] = useState(['-'])
    var arrayOptionsYears = ['2022', '2023', '2024', '2025', '2026', '2027', '2028', '2029', '2030',
        '2031', '2032', '2033', '2034', '2035', '2036', '2037', '2038', '2039', '2040']
    var arrayOptionsMonths = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
    function submit(data){
        data.date = data.year + '.' + data.month
        console.log(data)
        axios.post('http://localhost:8100/admin/getStatistic', data)
            .then(res => {
                setTextResult(res.data)
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
            <Form.Text style={{margin: '3px', marginLeft:'9px'}}>
                {textResult.map((text) => <div>{text}</div>)}
            </Form.Text>
        </Form.Group>
        <Button variant="light" type="submit">
            Показать продажи
        </Button>

    </Form>;
}