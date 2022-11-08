import './AddProduct.css';
import { React, useState } from 'react';
import { Form, Button, Container } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom';
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

const AddProductForm = () => {
    const navigate = useNavigate()
    const user = JSON.parse(localStorage.getItem("user"));
    const [errorsD, setErrorsD] = useState();
    const [formData, setFormData] = useState({
        name: '',
        shortDescription: "",
        detailedDescription: "",
        category: "",
        startingPrice: "",
        bidEndDate: "",
        sellerEmailAddress: user.emailAddress,
        "sellerName": user.firstName + " " + user.surname,
        "sellerCity": user.city
    })

    const handleAddProduct = (e, id) => {
        e.preventDefault()
        console.log(formData);
        let url = 'http://localhost:8600/e-auction/api/v1/seller/add-product';
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(formData),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then(response => {
                if (!response.ok) {
                    // get error message from body or default to response status
                    response.json().then(res => {
                        console.log(res)
                        setErrorsD(res.errors);
                    });
                } else {
                    navigate('/')
                }
            });
        // setFormData({
        //     name: '',
        //     description: "",
        //     image: "",
        //     category_id: 0,
        //     meal_ingredients: [],
        //     id: null
        // })
    }

    return (
        <Container className='addProduct'>
             {errorsD ? errorsD.map(err => <h5 style={{ color: 'red' }}>{err}</h5>) : ""}
            <Form onSubmit={handleAddProduct}>
                <Form.Group controlId="formName" className='addProductForm'>
                    <Form.Label column="lg">Product name</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, name: e.target.value })} type="text" placeholder="Enter product name" />
                </Form.Group>
                <Form.Group controlId="formName" className='addProductForm'>
                    <Form.Label column="lg">Short Description</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, shortDescription: e.target.value })} type="text" placeholder="Enter short description" />
                </Form.Group>
                <Form.Group controlId="formName" className='addProductForm'>
                    <Form.Label column="lg">Detailed Description</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, detailedDescription: e.target.value })} type="text" placeholder="Enter detailed description" />
                </Form.Group>
                <Form.Group controlId="formName" className='addProductForm'>
                    <Form.Label column="lg">Category</Form.Label>
                    <br></br>
                    <Form.Select class = "addSelect" aria-label="Default select example" onChange={e => setFormData({ ...formData, category: e.target.value })}>
                        <option>Select a product</option>
                        <option value="Painting">Painting</option>
                        <option value="Sculptor">Sculptor</option>
                        <option value="Ornaments">Ornaments</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group controlId="formName" className='addProductForm'>
                    <Form.Label column="lg">Starting Price</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, startingPrice: e.target.value })} type="text" placeholder="Enter starting price" />
                </Form.Group>
                <Form.Group controlId="formName" className='addProductForm'>
                    <Form.Label column="lg">Bid end date</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, bidEndDate: e.target.value })} type="text" placeholder="Bid end date - dd/mm/yyyy" />
                </Form.Group>
                <div className='addProductButton'>
                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </div>

            </Form>

        </Container>
    );
}
export default AddProductForm;