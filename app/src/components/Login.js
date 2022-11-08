import React, { useState } from 'react';
import './Login.css';
import PropTypes from 'prop-types';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

async function authenticate(credentials) {
    let url = 'http://localhost:8600/oauth/token?grant_type=password&username=' + credentials.username + '&password=' + credentials.password + '&scope=read'
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic b2F1dGhjbGllbnQxOm9hdXRoc2VjcmV0MQ=='
        }
    })
        .then(data => data.json())
}

export default function Login({ setToken }) {
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();
    const [error, setError] = useState();

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            const response = await authenticate({
                username,
                password
            });
           if(response.error) {
            setError(true);
           } else {
            localStorage.setItem('emailAddress', username);
            setToken(response.access_token);
            fetchUserDetails();
           }
        } catch (error) {
            setError(true);
            console.log(error);
        }

    }

    const fetchUserDetails = async () => {
        try {
            const response = await fetch("http://localhost:8600/users/" + localStorage.getItem("emailAddress"), {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
            response.json().then(res => {
                localStorage.setItem("user", JSON.stringify(res));
                return response;
            })
        } catch (error) {
            console.log(error);
        }
    }

    // return (
    //     <div className="login-wrapper">
    //         <h1>Please Log In</h1>
    //         {error ? <h3 style={{ color: 'red' }}>Invalid Username/password</h3> : ""}
    //         <form onSubmit={handleSubmit}>
    //             <div>
    //                 <label>
    //                     <p>Username</p>
    //                     <input type="text" onChange={e => setUserName(e.target.value)} />
    //                 </label></div>
    //             <div><label>
    //                 <p>Password</p>
    //                 <input type="password" onChange={e => setPassword(e.target.value)} />
    //             </label></div>
    //             <div className='submitButton'>
    //                 <button type="submit">Submit</button>
    //             </div>
    //         </form>
    //     </div>
    // )

    return (
        <Form className='login-form' onSubmit={handleSubmit}>
            <h2>Login</h2>
            {error ? <h3 style={{ color: 'red' }}>Invalid Username/password</h3> : ""}
            <br></br>
          <Form.Group className="mb-3" controlId="formBasicEmail" >
            <Form.Label>Email address</Form.Label>
            <Form.Control type="email" placeholder="Enter email" onChange={e => setUserName(e.target.value)}/>
            <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text>
          </Form.Group>
    
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control type="password" placeholder="Password"  onChange={e => setPassword(e.target.value)}/>
          </Form.Group>
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>
      );
}


Login.propTypes = {
    setToken: PropTypes.func.isRequired
}