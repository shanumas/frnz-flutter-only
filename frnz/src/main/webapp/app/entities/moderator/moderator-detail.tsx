import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './moderator.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ModeratorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const moderatorEntity = useAppSelector(state => state.moderator.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="moderatorDetailsHeading">
          <Translate contentKey="frnzApp.moderator.detail.title">Moderator</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{moderatorEntity.id}</dd>
          <dt>
            <Translate contentKey="frnzApp.moderator.gang">Gang</Translate>
          </dt>
          <dd>{moderatorEntity.gang ? moderatorEntity.gang.name : ''}</dd>
          <dt>
            <Translate contentKey="frnzApp.moderator.member">Member</Translate>
          </dt>
          <dd>{moderatorEntity.member ? moderatorEntity.member.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/moderator" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/moderator/${moderatorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ModeratorDetail;
