import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './place.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const placeEntity = useAppSelector(state => state.place.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="placeDetailsHeading">
          <Translate contentKey="frnzApp.place.detail.title">Place</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{placeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="frnzApp.place.name">Name</Translate>
            </span>
          </dt>
          <dd>{placeEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="frnzApp.place.address">Address</Translate>
            </span>
          </dt>
          <dd>{placeEntity.address}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="frnzApp.place.type">Type</Translate>
            </span>
          </dt>
          <dd>{placeEntity.type}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="frnzApp.place.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{placeEntity.phone}</dd>
          <dt>
            <span id="privatePlace">
              <Translate contentKey="frnzApp.place.privatePlace">Private Place</Translate>
            </span>
          </dt>
          <dd>{placeEntity.privatePlace ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/place" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/place/${placeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaceDetail;
