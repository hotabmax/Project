import React from "react";
import {Container, Nav, Navbar} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
export function Menubar(){

    return <Navbar bg="light" expand="lg">
        <Container>
            <Nav>
                <Nav.Link href="/admin">Пользователи</Nav.Link>
                <Nav.Link href="/adminAddOrDeleteNomenclature">Номенклатура</Nav.Link>
                <Nav.Link href="/adminDeleteHistoryOfPurchase">История закупок</Nav.Link>
                <Nav.Link href="/adminDeleteHistoryOfSelling">История продаж</Nav.Link>
                <Nav.Link href="/adminGetStatistic">Статистика</Nav.Link>
                <Nav.Link href="/exit">Выход</Nav.Link>
            </Nav>
        </Container>
    </Navbar>;
}