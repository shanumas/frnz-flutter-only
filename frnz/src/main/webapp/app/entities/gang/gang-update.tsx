import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IMember } from 'app/shared/model/member.model';
import { getEntities as getMembers } from 'app/entities/member/member.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { getEntities as getEvents } from 'app/entities/event/event.reducer';
import { getEntity, updateEntity, createEntity, reset } from './gang.reducer';
import { IGang } from 'app/shared/model/gang.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const GangUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const members = useAppSelector(state => state.member.entities);
  const events = useAppSelector(state => state.event.entities);
  const gangEntity = useAppSelector(state => state.gang.entity);
  const loading = useAppSelector(state => state.gang.loading);
  const updating = useAppSelector(state => state.gang.updating);
  const updateSuccess = useAppSelector(state => state.gang.updateSuccess);
  const handleClose = () => {
    props.history.push('/gang');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getMembers({}));
    dispatch(getEvents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...gangEntity,
      ...values,
      users: mapIdList(values.users),
      members: mapIdList(values.members),
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
          ...gangEntity,
          users: gangEntity?.users?.map(e => e.id.toString()),
          members: gangEntity?.members?.map(e => e.id.toString()),
          events: gangEntity?.events?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="frnzApp.gang.home.createOrEditLabel" data-cy="GangCreateUpdateHeading">
            <Translate contentKey="frnzApp.gang.home.createOrEditLabel">Create or edit a Gang</Translate>
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
                  id="gang-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('frnzApp.gang.name')}
                id="gang-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                }}
              />
              <ValidatedField
                label={translate('frnzApp.gang.handle')}
                id="gang-handle"
                name="handle"
                data-cy="handle"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                }}
              />
              <ValidatedField
                label={translate('frnzApp.gang.htmlContent')}
                id="gang-htmlContent"
                name="htmlContent"
                data-cy="htmlContent"
                type="textarea"
              />
              <ValidatedField
                label={translate('frnzApp.gang.description')}
                id="gang-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('frnzApp.gang.announcement')}
                id="gang-announcement"
                name="announcement"
                data-cy="announcement"
                type="text"
              />
              <ValidatedField label={translate('frnzApp.gang.logo')} id="gang-logo" name="logo" data-cy="logo" type="text" />
              <ValidatedField label={translate('frnzApp.gang.user')} id="gang-user" data-cy="user" type="select" multiple name="users">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('frnzApp.gang.member')}
                id="gang-member"
                data-cy="member"
                type="select"
                multiple
                name="members"
              >
                <option value="" key="0" />
                {members
                  ? members.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label={translate('frnzApp.gang.event')} id="gang-event" data-cy="event" type="select" multiple name="events">
                <option value="" key="0" />
                {events
                  ? events.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gang" replace color="info">
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

export default GangUpdate;
