import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { IParticipant } from 'app/shared/model/participant.model';
import { getEntities as getParticipants } from 'app/entities/participant/participant.reducer';
import { IGang } from 'app/shared/model/gang.model';
import { getEntities as getGangs } from 'app/entities/gang/gang.reducer';
import { getEntity, updateEntity, createEntity, reset } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EventUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const places = useAppSelector(state => state.place.entities);
  const participants = useAppSelector(state => state.participant.entities);
  const gangs = useAppSelector(state => state.gang.entities);
  const eventEntity = useAppSelector(state => state.event.entity);
  const loading = useAppSelector(state => state.event.loading);
  const updating = useAppSelector(state => state.event.updating);
  const updateSuccess = useAppSelector(state => state.event.updateSuccess);

  const handleClose = () => {
    props.history.push('/event');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPlaces({}));
    dispatch(getParticipants({}));
    dispatch(getGangs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);
    const entity = {
      ...eventEntity,
      ...values,
      place: places.find(it => it.id.toString() === values.placeId.toString()),
      participant: participants.find(it => it.id.toString() === values.participantId.toString()),
      gang: gangs.find(it => it.id.toString() === values.gangId.toString()),
      nonmembers: values.members.join(';')
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
        date: displayDefaultDateTime(),
      }
      : {
        type: 'SOCIAL',
        ...eventEntity,
        date: convertDateTimeFromServer(eventEntity.date),
        placeId: eventEntity?.place?.id,
        participantId: eventEntity?.participant?.id,
        gangId: eventEntity?.gang?.id,
        members: eventEntity?.nonmembers ? (eventEntity.nonmembers.split(';').length > 0 ? (eventEntity.nonmembers.split(';')) : ([';', ';', ';'])) : [],
      };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="frnzApp.event.home.createOrEditLabel" data-cy="EventCreateUpdateHeading">
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <div style={{ position: 'fixed', right: '-60px', top: '400px', }}>
                <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/event" replace color="info" className="w-50">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating} className="w-50">
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </div>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="event-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('frnzApp.event.date')}
                id="event-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="event-place" name="placeId" data-cy="place" label={translate('frnzApp.event.place')} type="select">
                <option value="" key="0" />
                {places
                  ? places.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.name}
                    </option>
                  ))
                  : null}
              </ValidatedField>
              <ValidatedField label={translate('frnzApp.event.name')} id="event-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                id="event-participant"
                name="participantId"
                data-cy="participant"
                label={translate('frnzApp.event.participant')}
                type="select"
              >
                <option value="" key="0" />
                {participants
                  ? participants.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.member.name}
                    </option>
                  ))
                  : null}
              </ValidatedField>
              {
                eventEntity.nonmembers?.split(';')?.map(function (object, index: number) {
                  const item = 'members[' + index.toString() + ']'
                  return <ValidatedField className="col-4 d-inline-block" key={index}
                    id={item}
                    name={item}
                    data-cy={item}
                    type="text"
                  />
                })
              }
              <ValidatedField label={translate('frnzApp.event.cost')} id="event-cost" name="cost" data-cy="cost" type="text" />
              <ValidatedField label={translate('frnzApp.event.duration')} id="event-endTime" name="endTime" data-cy="endTime" type="text" />
              <ValidatedField
                label={translate('frnzApp.event.confirmed')}
                id="event-confirmed"
                name="confirmed"
                data-cy="confirmed"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('frnzApp.event.cancelled')}
                id="event-cancelled"
                name="cancelled"
                data-cy="cancelled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('frnzApp.event.recurring')}
                id="event-recurring"
                name="recurring"
                data-cy="recurring"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('frnzApp.event.minimum')} id="event-minimum" name="minimum" data-cy="minimum" type="text" />
              <ValidatedField label={translate('frnzApp.event.maximum')} id="event-maximum" name="maximum" data-cy="maximum" type="text" />
              <ValidatedField
                label={translate('frnzApp.event.bookLimit')}
                id="event-bookLimit"
                name="bookLimit"
                data-cy="bookLimit"
                type="text"
              />
              <ValidatedField label={translate('frnzApp.event.share')} id="event-share" name="share" data-cy="share" type="text" />
              <ValidatedField id="event-gang" name="gangId" data-cy="gang" label={translate('frnzApp.event.gang')} type="select">
                <option value="" key="0" />
                {gangs
                  ? gangs.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.name}
                    </option>
                  ))
                  : null}
              </ValidatedField>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EventUpdate;
