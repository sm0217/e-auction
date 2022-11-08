import './RegisterUser.css';
import { React, useState } from 'react';
import { Form, Button, Container } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom';

const RegisterUserForm = () => {
    const navigate = useNavigate()
    const [formData, setFormData] = useState({
        emailAddress: '',
        firstName: '',
        surname: '',
        address: '',
        city: '',
        state: '',
        pin: '',
        phoneNumber: '',
        role: '',
        password: ''
    })
    const [errorsD, setErrorsD] = useState();

    const handleRegisterUser = (e, id) => {
        e.preventDefault()
        console.log(formData);
        let url = 'http://localhost:8600/users';
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(formData),
            headers: {
                'Content-Type': 'application/json'
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
    }


    return (
        <Container className='registerUser'>
            <h2 className='registerUserForm'>Register</h2>

            <Form onSubmit={handleRegisterUser}>
                <Form.Group className='registerUserForm' controlId="formBasicEmail" >
                    <Form.Label>Email address</Form.Label>
                    <Form.Control type="email" placeholder="Enter email" onChange={e => setFormData({ ...formData, emailAddress: e.target.value })} />
                </Form.Group>
                <Form.Group controlId="formBasicPassword" className="registerUserForm">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" onChange={e => setFormData({ ...formData, password: e.target.value })} />
                </Form.Group>
                <Form.Group controlId="formName" className='registerUserForm'>
                    <Form.Label column="lg">First Name</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, firstName: e.target.value })} type="text" placeholder="Enter first name" />
                </Form.Group>
                <Form.Group controlId="formName" className='registerUserForm'>
                    <Form.Label column="lg">Surname</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, surname: e.target.value })} type="text" placeholder="Enter surname" />
                </Form.Group>
                <Form.Group controlId="formName" className='registerUserForm'>
                    <Form.Label column="lg">Address</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, address: e.target.value })} type="text" placeholder="Enter address" />
                </Form.Group>
                <Form.Group controlId="formName" className='registerUserForm'>
                    <Form.Label column="lg">City</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, city: e.target.value })} type="text" placeholder="Enter city" />
                </Form.Group>
                <Form.Group controlId="formName" className='registerUserForm'>
                    <Form.Label column="lg">State</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, state: e.target.value })} type="text" placeholder="Enter State" />
                </Form.Group>
                <Form.Group controlId="formName" className='registerUserForm'>
                    <Form.Label column="lg">Pin</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, pin: e.target.value })} type="text" placeholder="Enter postcode" />
                </Form.Group>
                <Form.Group controlId="formName" className='registerUserForm'>
                    <Form.Label column="lg">Phone number</Form.Label>
                    <Form.Control onChange={e => setFormData({ ...formData, phoneNumber: e.target.value })} type="text" placeholder="Enter Phone number" />
                </Form.Group>
                <Form.Label className='registerUserForm'>Role</Form.Label>
                {/* <input type="radio" value="SELLER" name="role" /> Seller
                    <input type="radio" value="BUYER" name="role" /> Buyer */}
                <div className='registerUserForm' onChange={e => setFormData({ ...formData, role: e.target.value })}>
                    <input type="radio" id="SELLER" name="SELLER" value="SELLER" />
                    <label for="SELLER" style={{ paddingRight: '5px' }}>SELLER</label>
                    <input type="radio" id="BUYER" name="BUYER" value="BUYER" />
                    <label for="BUYER">BUYER</label>
                </div>
                {errorsD ? errorsD.map(err => <h5 className='registerUserForm' style={{ color: 'red' }}>{err}</h5>) : ""}
                <div className='registerUserButton'>
                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </div>
            </Form>
        </Container >
    );
}
export default RegisterUserForm;