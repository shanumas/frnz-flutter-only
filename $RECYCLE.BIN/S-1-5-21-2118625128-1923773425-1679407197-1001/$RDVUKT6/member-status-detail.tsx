import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './member-status.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MemberStatusDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const memberStatusEntity = useAppSelector(state => state.memberStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="memberStatusDetailsHeading">
          <Translate contentKey="kompiApp.memberStatus.detail.title">MemberStatus</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{memberStatusEntity.id}</dd>
          <dt>
            <span id="sure">
              <Translate contentKey="kompiApp.memberStatus.sure">Sure</Translate>
            </span>
          </dt>
          <dd>{memberStatusEntity.sure ? 'true' : 'false'}</dd>
          <dt>
            <span id="booker">
              <Translate contentKey="kompiApp.memberStatus.booker">Booker</Translate>
            </span>
          </dt>
          <dd>{memberStatusEntity.booker ? 'true' : 'false'}</dd>
          <dt>
            <span id="waiting">
              <Translate contentKey="kompiApp.memberStatus.waiting">Waiting</Translate>
            </span>
          </dt>
          <dd>{memberStatusEntity.waiting ? 'true' : 'false'}</dd>
          <dt>
            <span id="share">
              <Translate contentKey="kompiApp.memberStatus.share">Share</Translate>
            </span>
          </dt>
          <dd>{memberStatusEntity.share}</dd>
          <dt>
            <Translate contentKey="kompiApp.memberStatus.member">Member</Translate>
          </dt>
          <dd>{memberStatusEntity.member ? memberStatusEntity.member.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/member-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/member-status/${memberStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MemberStatusDetail;
