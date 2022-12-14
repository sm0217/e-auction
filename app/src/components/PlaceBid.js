import { React, useState } from 'react';
import { Form, Button, Container } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom';
import "./PlaceBid.css";

const PlaceBid = (props) => {
    const navigate = useNavigate();
    const [errorsD, setErrorsD] = useState();
    const [formData, setFormData] = useState({
        productId: props.item.id,
        bidAmount: ""
    })

    const placeBid = (e, id) => {
        e.preventDefault()
        console.log(formData);
        props.edit ? callEditBid(formData, props, setFormData) : callPlaceBid(formData, props, setFormData, setErrorsD) ;
    }

    return (
        <div>
            <h2> {props.edit ? "Edit bid" : "Place bid"} </h2>
            <Container className='placeBid'>
            {errorsD ? errorsD.map(err => <h5 style={{ color: 'red' }}>{err}</h5>) : ""}
                <Form onSubmit={placeBid}>
                    <Form.Group controlId="formName" className='placeBidForm'>
                        <Form.Label column="lg">Bid amount</Form.Label>
                        <Form.Control onChange={e => setFormData({ ...formData, bidAmount: e.target.value })} type="text" placeholder="Enter bid amount" />
                    </Form.Group>
                    <div className='placeBidButton'>
                        <Button variant="primary" type="submit">
                            Submit
                        </Button>
                    </div>
                </Form>
            </Container>
        </div>
    );
}

export default PlaceBid;

function callPlaceBid(formData, props, setFormData, setErrorsD) {
    let url = 'http://localhost:8600/e-auction/api/v1/buyer/place-bid';
    fetch(url, {
        method: 'POST',
        body: JSON.stringify(formData),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }).then(response => {
        if (!response.ok) {
            // get error message from body or default to response status
            response.json().then(res => {
                console.log(res.errors)
                setErrorsD(res.errors);
            });
            props.setChanged(props.item, false)
        } else {
            console.log(response)
            props.setChanged(props.item, false)
        }
    });
    // setFormData({
    //     productId: props.item.id,
    //     bidAmount: ""
    // });
}

function callEditBid(formData, props, setFormData) {
    let url = "http://localhost:8600/e-auction/api/v1/buyer/update-bid/" + props.item.id + "/" + localStorage.getItem("emailAddress") + "/" + formData.bidAmount;
    fetch(url, {
        method: 'PATCH',
        body: JSON.stringify(formData),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }).then(res => props.setChanged(props.item, false));
    // setFormData({
    //     productId: props.item.id,
    //     bidAmount: ""
    // });
}
