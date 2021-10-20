import React from 'react'
import { TextFormat } from 'react-jhipster';
import { APP_DATE_FORMAT } from 'app/config/constants';
import './events.scss';

import {
  Card, CardImg, CardText, CardBody,
  CardTitle, CardSubtitle, CardFooter, ListGroup, ListGroupItem
} from 'reactstrap';

const Eventitemnew = (event) => {
  return (
    <div className="frame">
  <div className="center">

		<div className="profile">
			<div className="image">
				<div className="circle-1"></div>
				<div className="circle-2"></div>
				<img src="https://100dayscss.com/codepen/jessica-potter.jpg" width="70" height="70" alt="Jessica Potter"/>
			</div>

			<div className="name">Jessica Potter</div>
			<div className="job">Visual Artist</div>

			<div className="actions">
				<button className="btn">Follow</button>
				<button className="btn">Message</button>
			</div>
		</div>

		<div className="stats">
			<div className="box">
				<span className="value">523</span>
				<span className="parameter">Posts</span>
			</div>
			<div className="box">
				<span className="value">1387</span>
				<span className="parameter">Likes</span>
			</div>
			<div className="box">
				<span className="value">146</span>
				<span className="parameter">Follower</span>
			</div>
		</div>
  </div>
</div>
  )
}

export default Eventitemnew
