import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './participant.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ParticipantDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const participantEntity = useAppSelector(state => state.participant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="participantDetailsHeading">
          <Translate contentKey="frnzApp.participant.detail.title">Participant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{participantEntity.id}</dd>
          <dt>
            <span id="sure">
              <Translate contentKey="frnzApp.participant.sure">Sure</Translate>
            </span>
          </dt>
          <dd>{participantEntity.sure ? 'true' : 'false'}</dd>
          <dt>
            <span id="host">
              <Translate contentKey="frnzApp.participant.host">Host</Translate>
            </span>
          </dt>
          <dd>{participantEntity.host ? 'true' : 'false'}</dd>
          <dt>
            <span id="booker">
              <Translate contentKey="frnzApp.participant.booker">Booker</Translate>
            </span>
          </dt>
          <dd>{participantEntity.booker ? 'true' : 'false'}</dd>
          <dt>
            <span id="waiting">
              <Translate contentKey="frnzApp.participant.waiting">Waiting</Translate>
            </span>
          </dt>
          <dd>{participantEntity.waiting ? 'true' : 'false'}</dd>
          <dt>
            <span id="share">
              <Translate contentKey="frnzApp.participant.share">Share</Translate>
            </span>
          </dt>
          <dd>{participantEntity.share}</dd>
          <dt>
            <Translate contentKey="frnzApp.participant.member">Member</Translate>
          </dt>
          <dd>{participantEntity.member ? participantEntity.member.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/participant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/participant/${participantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParticipantDetail;
