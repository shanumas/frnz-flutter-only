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
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="frnzApp.event.home.createOrEditLabel" data-cy="EventCreateUpdateHeading">
            <Translate contentKey="frnzApp.event.home.createOrEditLabel">Create or edit a Event</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
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
              <ValidatedField label={translate('frnzApp.event.type')} id="event-type" name="type" data-cy="type" type="select">
                <option value="SOCIAL">{translate('frnzApp.EventType.SOCIAL')}</option>
                <option value="SPORT">{translate('frnzApp.EventType.SPORT')}</option>
                <option value="EXERCISE">{translate('frnzApp.EventType.EXERCISE')}</option>
                <option value="COMPETITION">{translate('frnzApp.EventType.COMPETITION')}</option>
                <option value="COMMUNITY">{translate('frnzApp.EventType.COMMUNITY')}</option>
                <option value="OTHERS">{translate('frnzApp.EventType.OTHERS')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('frnzApp.event.date')}
                id="event-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('frnzApp.event.name')} id="event-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('frnzApp.event.startTime')}
                id="event-startTime"
                name="startTime"
                data-cy="startTime"
                type="text"
              />
              <ValidatedField label={translate('frnzApp.event.endTime')} id="event-endTime" name="endTime" data-cy="endTime" type="text" />
              <ValidatedField
                label={translate('frnzApp.event.nonmembers')}
                id="event-nonmembers"
                name="nonmembers"
                data-cy="nonmembers"
                type="text"
              />
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
              <ValidatedField label={translate('frnzApp.event.cost')} id="event-cost" name="cost" data-cy="cost" type="text" />
              <ValidatedField label={translate('frnzApp.event.share')} id="event-share" name="share" data-cy="share" type="text" />
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
                        {otherEntity.member}
                      </option>
                    ))
                  : null}
              </ValidatedField>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/event" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EventUpdate;
