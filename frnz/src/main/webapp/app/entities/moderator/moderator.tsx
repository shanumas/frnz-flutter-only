import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './moderator.reducer';
import { IModerator } from 'app/shared/model/moderator.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Moderator = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const moderatorList = useAppSelector(state => state.moderator.entities);
  const loading = useAppSelector(state => state.moderator.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="moderator-heading" data-cy="ModeratorHeading">
        <Translate contentKey="frnzApp.moderator.home.title">Moderators</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="frnzApp.moderator.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="frnzApp.moderator.home.createLabel">Create new Moderator</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {moderatorList && moderatorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="frnzApp.moderator.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.moderator.gang">Gang</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.moderator.member">Member</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {moderatorList.map((moderator, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${moderator.id}`} color="link" size="sm">
                      {moderator.id}
                    </Button>
                  </td>
                  <td>{moderator.gang ? <Link to={`gang/${moderator.gang.id}`}>{moderator.gang.name}</Link> : ''}</td>
                  <td>{moderator.member ? <Link to={`member/${moderator.member.id}`}>{moderator.member.name}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${moderator.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${moderator.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${moderator.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="frnzApp.moderator.home.notFound">No Moderators found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Moderator;
