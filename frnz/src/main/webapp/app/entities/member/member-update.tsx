import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IEvent } from 'app/shared/model/event.model';
import { getEntities as getEvents } from 'app/entities/event/event.reducer';
import { IGang } from 'app/shared/model/gang.model';
import { getEntities as getGangs } from 'app/entities/gang/gang.reducer';
import { getEntity, updateEntity, createEntity, reset } from './member.reducer';
import { IMember } from 'app/shared/model/member.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MemberUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const events = useAppSelector(state => state.event.entities);
  const gangs = useAppSelector(state => state.gang.entities);
  const memberEntity = useAppSelector(state => state.member.entity);
  const loading = useAppSelector(state => state.member.loading);
  const updating = useAppSelector(state => state.member.updating);
  const updateSuccess = useAppSelector(state => state.member.updateSuccess);
  const handleClose = () => {
    props.history.push('/member');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEvents({}));
    dispatch(getGangs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...memberEntity,
      ...values,
      events: mapIdList(values.events),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...memberEntity,
          events: memberEntity?.events?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="frnzApp.member.home.createOrEditLabel" data-cy="MemberCreateUpdateHeading">
            <Translate contentKey="frnzApp.member.home.createOrEditLabel">Create or edit a Member</Translate>
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
                  id="member-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('frnzApp.member.name')}
                id="member-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                }}
              />
              <ValidatedField label={translate('frnzApp.member.email')} id="member-email" name="email" data-cy="email" type="text" />
              <ValidatedField label={translate('frnzApp.member.phone')} id="member-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField
                label={translate('frnzApp.member.guest')}
                id="member-guest"
                name="guest"
                data-cy="guest"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('frnzApp.member.event')}
                id="member-event"
                data-cy="event"
                type="select"
                multiple
                name="events"
              >
                <option value="" key="0" />
                {events
                  ? events.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/member" replace color="info">
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

export default MemberUpdate;
