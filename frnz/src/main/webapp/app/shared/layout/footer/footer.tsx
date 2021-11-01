import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content fixed-bottom">
    <Row className="d-flex justify-content-around">
      <Col className="bg-info text-white col-2" style={{fontWeight: 'bold', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        Filters
      </Col>
      <Col className="footer-card col-2" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <img src="content/images/racket_32.png" alt="Badminton" />
      </Col>
      <Col className="footer-card col-2" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <img src="content/images/cinema_32.png" alt="Cinema" />
      </Col>
      <Col className="footer-card col-2" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <img src="content/images/dinner_32.png" alt="Dinner" />
      </Col>
      <Col className="footer-card col-2" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <img src="content/images/group_32.png" alt="Outdoor" />
      </Col>
    </Row>
  </div>
);

export default Footer;
