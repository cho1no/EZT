import React from "react";
import {
  Navbar,
  Nav,
  Button,
  Form,
  FormControl,
  InputGroup,
  Dropdown,
} from "react-bootstrap";
import { Link } from "react-router-dom";

function Header() {
  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="sb-topnav">
      <Navbar.Brand className="ps-3" as={Link} to="/">
        Start Bootstrap
      </Navbar.Brand>
      <Button
        variant="link"
        className="btn-sm order-1 order-lg-0 me-4 me-lg-0"
        id="sidebarToggle"
      >
        <i className="fas fa-bars"></i>
      </Button>
      <Form className="d-none d-md-inline-block ms-auto me-0 me-md-3 my-2 my-md-0">
        <InputGroup>
          <FormControl
            type="text"
            placeholder="Search for..."
            aria-label="Search for..."
            aria-describedby="btnNavbarSearch"
          />
          <Button variant="primary" id="btnNavbarSearch" type="button">
            <i className="fas fa-search"></i>
          </Button>
        </InputGroup>
      </Form>
      <Nav className="ms-auto ms-md-0 me-3 me-lg-4">
        <Dropdown align="end">
          <Dropdown.Toggle as={Nav.Link} id="navbarDropdown" role="button">
            <i className="fas fa-user fa-fw"></i>
          </Dropdown.Toggle>
          <Dropdown.Menu>
            <Dropdown.Item as={Link} to="/settings">
              Settings
            </Dropdown.Item>
            <Dropdown.Item as={Link} to="/activity-log">
              Activity Log
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item as={Link} to="/logout">
              Logout
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Nav>
    </Navbar>
  );
}

export default Header;
