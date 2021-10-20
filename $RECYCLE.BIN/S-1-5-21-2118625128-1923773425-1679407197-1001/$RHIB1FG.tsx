import React from 'react'
import { TextFormat, Translate } from 'react-jhipster';
import { APP_DATE_FORMAT } from 'app/config/constants';
import './events.scss';
import { useState } from 'react';

import {
  Card, CardImg, CardText, CardBody,
  CardTitle, CardSubtitle, CardFooter, ListGroup, ListGroupItem, Button
} from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';
import { propTypes } from 'react-bootstrap/esm/Image';

const Eventitem = (props) => {
  const [eventDetail] = useState(props.detail);

  return (
    <div className="card p-0 mb-2 rounded shadow-sm p-1 m-0">
    </div>
  )
}

export default Eventitem
