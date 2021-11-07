import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { IGang } from 'app/shared/model/gang.model';
import { getEntities as getGangs } from 'app/entities/gang/gang.reducer';
import { IMember } from 'app/shared/model/member.model';
import { getEntities as getMembers } from 'app/entities/member/member.reducer';
import { getEntity, updateEntity, createEntity, reset } from './event.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { EventType } from 'app/shared/model/enumerations/event-type.model';

export const EventUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const places = useAppSelector(state => state.place.entities);
  const gangs = useAppSelector(state => state.gang.entities);
  const members = useAppSelector(state => state.member.entities);
  const eventEntity = useAppSelector(state => state.event.entity);
  const loading = useAppSelector(state => state.event.loading);
  const updating = useAppSelector(state => state.event.updating);
  const updateSuccess = useAppSelector(state => state.event.updateSuccess);
  const eventTypeValues = Object.keys(EventType);
  const handleClose = () => {
    props.history.push('/event');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPlaces({}));
    dispatch(getGangs({}));
    dispatch(getMembers({}));
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
      place: places.find(it => it.id.toString() === values.place.toString()),
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
          place: eventEntity?.place?.id,
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
                {eventTypeValues.map(eventType => (
                  <option value={eventType} key={eventType}>
                    {translate('frnzApp.EventType' + eventType)}
                  </option>
                ))}
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
              <ValidatedField label={translate('frnzApp.event.minimum')} id="event-minimum" name="minimum" data-cy="minimum" type="text" />
              <ValidatedField label={translate('frnzApp.event.maximum')} id="event-maximum" name="maximum" data-cy="maximum" type="text" />
              <ValidatedField label={translate('frnzApp.event.ideal')} id="event-ideal" name="ideal" data-cy="ideal" type="text" />
              <ValidatedField label={translate('frnzApp.event.cost')} id="event-cost" name="cost" data-cy="cost" type="text" />
              <ValidatedField label={translate('frnzApp.event.share')} id="event-share" name="share" data-cy="share" type="text" />
              <ValidatedField id="event-place" name="place" data-cy="place" label={translate('frnzApp.event.place')} type="select">
                <option value="" key="0" />
                {places
                  ? places.map(otherEntity => (
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
