import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './event.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EventDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const eventEntity = useAppSelector(state => state.event.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventDetailsHeading">
          <Translate contentKey="frnzApp.event.detail.title">Event</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{eventEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="frnzApp.event.type">Type</Translate>
            </span>
          </dt>
          <dd>{eventEntity.type}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="frnzApp.event.date">Date</Translate>
            </span>
          </dt>
          <dd>{eventEntity.date ? <TextFormat value={eventEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="frnzApp.event.name">Name</Translate>
            </span>
          </dt>
          <dd>{eventEntity.name}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="frnzApp.event.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>{eventEntity.startTime}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="frnzApp.event.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{eventEntity.endTime}</dd>
          <dt>
            <span id="nonmembers">
              <Translate contentKey="frnzApp.event.nonmembers">Nonmembers</Translate>
            </span>
          </dt>
          <dd>{eventEntity.nonmembers}</dd>
          <dt>
            <span id="confirmed">
              <Translate contentKey="frnzApp.event.confirmed">Confirmed</Translate>
            </span>
          </dt>
          <dd>{eventEntity.confirmed ? 'true' : 'false'}</dd>
          <dt>
            <span id="cancelled">
              <Translate contentKey="frnzApp.event.cancelled">Cancelled</Translate>
            </span>
          </dt>
          <dd>{eventEntity.cancelled ? 'true' : 'false'}</dd>
          <dt>
            <span id="recurring">
              <Translate contentKey="frnzApp.event.recurring">Recurring</Translate>
            </span>
          </dt>
          <dd>{eventEntity.recurring ? 'true' : 'false'}</dd>
          <dt>
            <span id="minimum">
              <Translate contentKey="frnzApp.event.minimum">Minimum</Translate>
            </span>
          </dt>
          <dd>{eventEntity.minimum}</dd>
          <dt>
            <span id="maximum">
              <Translate contentKey="frnzApp.event.maximum">Maximum</Translate>
            </span>
          </dt>
          <dd>{eventEntity.maximum}</dd>
          <dt>
            <span id="bookLimit">
              <Translate contentKey="frnzApp.event.bookLimit">Book Limit</Translate>
            </span>
          </dt>
          <dd>{eventEntity.bookLimit}</dd>
          <dt>
            <span id="cost">
              <Translate contentKey="frnzApp.event.cost">Cost</Translate>
            </span>
          </dt>
          <dd>{eventEntity.cost}</dd>
          <dt>
            <span id="share">
              <Translate contentKey="frnzApp.event.share">Share</Translate>
            </span>
          </dt>
          <dd>{eventEntity.share}</dd>
          <dt>
            <Translate contentKey="frnzApp.event.place">Place</Translate>
          </dt>
          <dd>{eventEntity.place ? eventEntity.place.name : ''}</dd>
          <dt>
            <Translate contentKey="frnzApp.event.participant">Participant</Translate>
          </dt>
          <dd>{eventEntity.participant ? eventEntity.participant.member : ''}</dd>
          <dt>
            <Translate contentKey="frnzApp.event.gang">Gang</Translate>
          </dt>
          <dd>{eventEntity.gang ? eventEntity.gang.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/event" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/event/${eventEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventDetail;
