import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './member.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MemberDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const memberEntity = useAppSelector(state => state.member.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="memberDetailsHeading">
          <Translate contentKey="frnzApp.member.detail.title">Member</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{memberEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="frnzApp.member.name">Name</Translate>
            </span>
          </dt>
          <dd>{memberEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="frnzApp.member.email">Email</Translate>
            </span>
          </dt>
          <dd>{memberEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="frnzApp.member.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{memberEntity.phone}</dd>
          <dt>
            <span id="guest">
              <Translate contentKey="frnzApp.member.guest">Guest</Translate>
            </span>
          </dt>
          <dd>{memberEntity.guest ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="frnzApp.member.event">Event</Translate>
          </dt>
          <dd>
            {memberEntity.events
              ? memberEntity.events.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {memberEntity.events && i === memberEntity.events.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/member" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/member/${memberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MemberDetail;
