import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IGang } from 'app/shared/model/gang.model';
import { getEntities as getGangs } from 'app/entities/gang/gang.reducer';
import { IMember } from 'app/shared/model/member.model';
import { getEntities as getMembers } from 'app/entities/member/member.reducer';
import { getEntity, updateEntity, createEntity, reset } from './moderator.reducer';
import { IModerator } from 'app/shared/model/moderator.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ModeratorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const gangs = useAppSelector(state => state.gang.entities);
  const members = useAppSelector(state => state.member.entities);
  const moderatorEntity = useAppSelector(state => state.moderator.entity);
  const loading = useAppSelector(state => state.moderator.loading);
  const updating = useAppSelector(state => state.moderator.updating);
  const updateSuccess = useAppSelector(state => state.moderator.updateSuccess);
  const handleClose = () => {
    props.history.push('/moderator');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getGangs({}));
    dispatch(getMembers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...moderatorEntity,
      ...values,
      gang: gangs.find(it => it.id.toString() === values.gang.toString()),
      member: members.find(it => it.id.toString() === values.member.toString()),
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
          ...moderatorEntity,
          gang: moderatorEntity?.gang?.id,
          member: moderatorEntity?.member?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="frnzApp.moderator.home.createOrEditLabel" data-cy="ModeratorCreateUpdateHeading">
            <Translate contentKey="frnzApp.moderator.home.createOrEditLabel">Create or edit a Moderator</Translate>
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
                  id="moderator-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField id="moderator-gang" name="gang" data-cy="gang" label={translate('frnzApp.moderator.gang')} type="select">
                <option value="" key="0" />
                {gangs
                  ? gangs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.handle}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="moderator-member"
                name="member"
                data-cy="member"
                label={translate('frnzApp.moderator.member')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/moderator" replace color="info">
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

export default ModeratorUpdate;
