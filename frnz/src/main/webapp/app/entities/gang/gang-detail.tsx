import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './gang.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GangDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const gangEntity = useAppSelector(state => state.gang.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gangDetailsHeading">
          <Translate contentKey="frnzApp.gang.detail.title">Gang</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{gangEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="frnzApp.gang.name">Name</Translate>
            </span>
          </dt>
          <dd>{gangEntity.name}</dd>
          <dt>
            <span id="handle">
              <Translate contentKey="frnzApp.gang.handle">Handle</Translate>
            </span>
          </dt>
          <dd>{gangEntity.handle}</dd>
          <dt>
            <span id="htmlContent">
              <Translate contentKey="frnzApp.gang.htmlContent">Html Content</Translate>
            </span>
          </dt>
          <dd>{gangEntity.htmlContent}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="frnzApp.gang.description">Description</Translate>
            </span>
          </dt>
          <dd>{gangEntity.description}</dd>
          <dt>
            <span id="announcement">
              <Translate contentKey="frnzApp.gang.announcement">Announcement</Translate>
            </span>
          </dt>
          <dd>{gangEntity.announcement}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="frnzApp.gang.logo">Logo</Translate>
            </span>
          </dt>
          <dd>{gangEntity.logo}</dd>
          <dt>
            <Translate contentKey="frnzApp.gang.user">User</Translate>
          </dt>
          <dd>
            {gangEntity.users
              ? gangEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.login}</a>
                    {gangEntity.users && i === gangEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="frnzApp.gang.member">Member</Translate>
          </dt>
          <dd>
            {gangEntity.members
              ? gangEntity.members.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {gangEntity.members && i === gangEntity.members.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="frnzApp.gang.event">Event</Translate>
          </dt>
          <dd>
            {gangEntity.events
              ? gangEntity.events.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {gangEntity.events && i === gangEntity.events.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/gang" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gang/${gangEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GangDetail;
