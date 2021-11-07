import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './participant.reducer';
import { IParticipant } from 'app/shared/model/participant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Participant = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const participantList = useAppSelector(state => state.participant.entities);
  const loading = useAppSelector(state => state.participant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="participant-heading" data-cy="ParticipantHeading">
        <Translate contentKey="frnzApp.participant.home.title">Participants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="frnzApp.participant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="frnzApp.participant.home.createLabel">Create new Participant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {participantList && participantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="frnzApp.participant.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.participant.sure">Sure</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.participant.host">Host</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.participant.booker">Booker</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.participant.waiting">Waiting</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.participant.share">Share</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.participant.member">Member</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.participant.event">Event</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {participantList.map((participant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${participant.id}`} color="link" size="sm">
                      {participant.id}
                    </Button>
                  </td>
                  <td>{participant.sure ? 'true' : 'false'}</td>
                  <td>{participant.host ? 'true' : 'false'}</td>
                  <td>{participant.booker ? 'true' : 'false'}</td>
                  <td>{participant.waiting ? 'true' : 'false'}</td>
                  <td>{participant.share}</td>
                  <td>{participant.member ? <Link to={`member/${participant.member.id}`}>{participant.member.name}</Link> : ''}</td>
                  <td>{participant.event ? <Link to={`event/${participant.event.id}`}>{participant.event.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${participant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${participant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${participant.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="frnzApp.participant.home.notFound">No Participants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Participant;
