import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {Form} from "react-bootstrap"
import {Box, Button, FormControl, InputLabel, MenuItem, Select} from "@material-ui/core"
import axios from "axios";
export function FormGetHistoryOfSelling({setTableData}){
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
        axios.post('http://localhost:8100/admin/getHistoryOfSelling', data)
            .then(res => {
                setTableData(res.data)
            })
    }
    return <Form onSubmit={handleSubmit(submit)}>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Год</InputLabel>
                    <Select
                        label="Год"
                        {...register('year')}
                    >
                        {arrayOptionsYears.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>Месяц</InputLabel>
                    <Select
                        label="Месяц"
                        {...register('month')}
                    >
                        {arrayOptionsMonths.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Form.Group>
            <Box>
                <FormControl variant="outlined" style={{marginTop:'10px', minWidth: '150px'}}>
                    <InputLabel>День</InputLabel>
                    <Select
                        label="День"
                        {...register('day')}
                    >
                        {arrayOptionsDays.map((option) => <MenuItem value={option}>{option}</MenuItem>)}
                    </Select>
                </FormControl>
            </Box>
        </Form.Group>
        <Button style={{marginTop:'10px'}} variant="outlined" type="submit">
            Показать продажи
        </Button>
    </Form>;
}