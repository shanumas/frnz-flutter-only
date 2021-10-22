import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMember } from 'app/shared/model/member.model';
import { getEntities as getMembers } from 'app/entities/member/member.reducer';
import { getEntity, updateEntity, createEntity, reset } from './participant.reducer';
import { IParticipant } from 'app/shared/model/participant.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ParticipantUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const members = useAppSelector(state => state.member.entities);
  const participantEntity = useAppSelector(state => state.participant.entity);
  const loading = useAppSelector(state => state.participant.loading);
  const updating = useAppSelector(state => state.participant.updating);
  const updateSuccess = useAppSelector(state => state.participant.updateSuccess);

  const handleClose = () => {
    props.history.push('/participant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMembers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...participantEntity,
      ...values,
      member: members.find(it => it.id.toString() === values.memberId.toString()),
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
          ...participantEntity,
          memberId: participantEntity?.member?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="frnzApp.participant.home.createOrEditLabel" data-cy="ParticipantCreateUpdateHeading">
            <Translate contentKey="frnzApp.participant.home.createOrEditLabel">Create or edit a Participant</Translate>
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
                  id="participant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('frnzApp.participant.sure')}
                id="participant-sure"
                name="sure"
                data-cy="sure"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('frnzApp.participant.host')}
                id="participant-host"
                name="host"
                data-cy="host"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('frnzApp.participant.booker')}
                id="participant-booker"
                name="booker"
                data-cy="booker"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('frnzApp.participant.waiting')}
                id="participant-waiting"
                name="waiting"
                data-cy="waiting"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('frnzApp.participant.share')}
                id="participant-share"
                name="share"
                data-cy="share"
                type="text"
              />
              <ValidatedField
                id="participant-member"
                name="memberId"
                data-cy="member"
                label={translate('frnzApp.participant.member')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/participant" replace color="info">
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

export default ParticipantUpdate;
