import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <div className="container">
        <div className="row">
          <div className="col-lg-12 text-center">
            <h2 className="text-uppercase section-heading text-primary">Welcome to FRNZ</h2>
            <p className="text-muted section-subheading">Currently FRNZ is available for limited users only.  Send us an email, we will contact you shortly.</p>
          </div>
        </div>
        <div className="row">
          <div className="col-lg-12">
            <form id="contactForm" name="contactForm">
              <div className="row">
                <div className="col-md-6">
                  <div className="form-group mb-3"><input className="form-control" type="email" id="email" placeholder="Your Email *" required /><small className="form-text text-danger help-block lead"></small></div>
                  <div className="form-group mb-3"><input className="form-control" type="text" id="name" placeholder="Your Name" required /><small className="form-text text-danger flex-grow-1 help-block lead"></small></div>
                  <div className="form-group mb-3"><input className="form-control" type="tel" placeholder="Your Phone" required /><small className="form-text text-danger help-block lead"></small></div>
                </div>
                <div className="col-md-6">
                  <div className="form-group mb-3"><textarea className="form-control" id="message" placeholder="Your Message" required></textarea><small className="form-text text-danger help-block lead"></small></div>
                </div>
                <div className="w-100"></div>
                <div className="col-lg-12 text-center">
                  <div id="success"></div><button className="btn btn-primary btn-xl text-uppercase" id="sendMessageButton" type="submit">Request link</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </Row>
  );
};

export default Home;
