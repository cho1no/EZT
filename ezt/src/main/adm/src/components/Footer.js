import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";

function Footer() {
  return (
    <footer className="py-4 bg-light mt-auto">
      <Container fluid className="px-4">
        <Row className="d-flex align-items-center justify-content-between small">
          <Col className="text-muted">Copyright &copy; Your Website 2023</Col>
          <Col className="d-flex justify-content-end">
            <Link to="/privacy-policy">Privacy Policy</Link>
            &middot;
            <Link to="/terms-and-conditions">Terms &amp; Conditions</Link>
          </Col>
        </Row>
      </Container>
    </footer>
  );
}

export default Footer;
