import React from 'react';
import Table from 'react-bootstrap/Table';
import './Product.css';
import PlaceBid from './PlaceBid';
import { Badge } from 'react-bootstrap';
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';

export default class Product extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            bidsLoaded: false,
            selectedItem: null,
            product: null,
            items: [],
            bids: null,
            edit: false,
            deleteError: ""
        };
        this.handleChange = this.handleChange.bind(this);
        this.displayBids = this.displayBids.bind(this);
        this.fetchBids = this.fetchBids.bind(this);
        this.setSelectedItem = this.setSelectedItem.bind(this);
        this.handleEditBid = this.handleEditBid.bind(this);
        this.handleDeleteProduct = this.handleDeleteProduct.bind(this);
        this.selectedProduct = this.selectedProduct.bind(this);
    }

    componentDidMount() {
        this.fetchProductList();
    }

    fetchProductList() {
        fetch("http://localhost:8600/e-auction/api/v1/products", {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        items: result
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            );
    }

    fetchBids(item) {
        let role = JSON.parse(localStorage.getItem("user")).role;
        fetch("http://localhost:8600/e-auction/api/v1/seller/show-bids/" + item.id, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then(res => res.json())
            .then(
                (result) => {
                    if (result.length !== 0) {
                        this.setState({
                            bidsLoaded: true,
                            bids: this.displayBids(result, role)
                        });
                    } else {
                        if (role === "SELLER") {
                            this.setState({
                                bidsLoaded: true,
                                bids: (<div><h3> No Bids found for the product </h3></div>)
                            });
                        } else {
                            this.setState({
                                bidsLoaded: true,
                                bids: (<div><PlaceBid item={item} setChanged={this.setSelectedItem} /></div>)
                            });
                        }

                    }
                },
                (error) => {
                    console.log(error);
                    this.setState({
                        bidsLoaded: true,
                        bids: (<h2>Unable to load bids</h2>)
                    });
                }
            );
    }

    render() {
        const { error, isLoaded, items, selectedItem, bidsLoaded, bids, edit, product, deleteError } = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <div className='pList'>
                    <div>
                        <h1>
                            Products{' '}
                            <a className='link' href="/add-product">Add Product</a>
                        </h1>
                    </div>
                    <select onChange={this.handleChange} className="productList">
                        <option value="Select a product"> -- Select a product -- </option>
                        {items.map((item) => <option key={item.id} value={item.id}>{item.name}</option>)}
                    </select>
                    {deleteError ? <h4 style={{ color: 'red' }}>{deleteError}</h4> : ""}
                    {isLoaded ? selectedItem : "Products Loading..."}
                    {bidsLoaded ? bids : ""}
                    {edit ? <PlaceBid item={product} edit={true} setChanged={this.setSelectedItem} /> : ""}
                </div>
            );
        }
    }

    handleChange = async (event) => {
        const { items } = this.state;
        let item = items.filter(i => i.id == event.target.value)[0];
        this.product(item);
    }

    handleEditBid = () => {
        this.setState({
            edit: true
        })
    }


    handleDeleteProduct(id) {
        const { items } = this.state;
        fetch("http://localhost:8600/e-auction/api/v1/seller/delete/" + id, {
            method: "DELETE",
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then(res => {
                if (!res.ok) {
                    console.log("get error message from body or default to response status")
                    res.json().then(res => {
                        console.log(res.errors[0])
                        this.setState({
                            deleteError: res.errors[0]
                        });
                    });
                } else {
                    console.log(res)
                    this.setState({
                        selectedItem: null,
                        items: items.filter(item => item.id !== id),
                        bidsLoaded: false
                    });
                }
            });
    }

    product(item) {
        let role = JSON.parse(localStorage.getItem("user")).role;
        this.setState({
            selectedItem: this.selectedProduct(item, role),
            product: item
        });
    }

    selectedProduct(item, role) {
        const { deleteError } = this.state;
        console.log(deleteError)
        return <div className='views'>
            <h3>Selected Product - {item.name}
                {role == "SELLER" ? <DeleteForeverIcon fontSize="large" onClick={() => this.handleDeleteProduct(item.id)}></DeleteForeverIcon> : ""}
            </h3>
            {this.displayProduct(item)}
            {role === "BUYER" ? this.displaySeller(item) : ""}
            {this.fetchBids(item)}
        </div>;
    }

    setSelectedItem(selectedItem, edit) {
        this.setState({
            edit: edit
        });
        this.product(selectedItem)
    }

    displayProduct(item) {
        return <Table striped bordered hover variant="dark">
            <thead>
                <tr>
                    <th>Product name</th>
                    <th>Short description</th>
                    <th>Detailed description</th>
                    <th>Category</th>
                    <th>Starting price</th>
                    <th>Bid end date</th>
                </tr>
            </thead>
            <tbody>
                <tr key={item.id}>
                    <td>{item.name}</td>
                    <td>{item.shortDescription}</td>
                    <td>{item.detailedDescription}</td>
                    <td>{item.category}</td>
                    <td>{'£' + item.startingPrice}</td>
                    <td className='endDate'>{item.bidEndDate.substring(0, 10)}</td>
                </tr>
            </tbody>
        </Table>;
    }

    displaySeller() {
        let user = JSON.parse(localStorage.getItem("user"))
        return <div>
            <h3>Seller Details</h3>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Seller name</th>
                        <th>Email address</th>
                        <th>City</th>
                        <th>Phone number</th>
                    </tr>
                </thead>
                <tbody>
                    <tr key={user.emailAddress}>
                        <td>{user.firstName}</td>
                        <td>{user.emailAddress}</td>
                        <td>{user.city}</td>
                        <td>{user.phoneNumber}</td>
                    </tr>
                </tbody>
            </Table>
        </div>;
    }

    displayBids(bids, role) {
        if (bids && bids.length !== 0) {
            return (
                <div>
                    <h3> Bids </h3>
                    <Table striped bordered hover variant="dark">
                        <thead>
                            <tr>
                                <th>Buyer</th>
                                <th>Bidding amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            {bids.map((bid) => {
                                return <tr>
                                    <td>{bid.emailAddress}</td>
                                    <td>{bid.bidAmount}
                                        {role == "BUYER" ? <EditIcon onClick={this.handleEditBid}></EditIcon> : ""}
                                    </td>
                                </tr>
                            })}
                        </tbody>
                    </Table>
                </div>
            );
        } else {
            return (<h3> No Bids found for the product </h3>)
        }
    }
}
