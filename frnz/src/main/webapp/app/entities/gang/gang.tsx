import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './gang.reducer';
import { IGang } from 'app/shared/model/gang.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Gang = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const gangList = useAppSelector(state => state.gang.entities);
  const loading = useAppSelector(state => state.gang.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="gang-heading" data-cy="GangHeading">
        <Translate contentKey="frnzApp.gang.home.title">Gangs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="frnzApp.gang.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="frnzApp.gang.home.createLabel">Create new Gang</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gangList && gangList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="frnzApp.gang.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.handle">Handle</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.htmlContent">Html Content</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.announcement">Announcement</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.logo">Logo</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.member">Member</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.gang.event">Event</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gangList.map((gang, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${gang.id}`} color="link" size="sm">
                      {gang.id}
                    </Button>
                  </td>
                  <td>{gang.name}</td>
                  <td>{gang.handle}</td>
                  <td>{gang.htmlContent}</td>
                  <td>{gang.description}</td>
                  <td>{gang.announcement}</td>
                  <td>{gang.logo}</td>
                  <td>
                    {gang.users
                      ? gang.users.map((val, j) => (
                          <span key={j}>
                            {val.login}
                            {j === gang.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {gang.members
                      ? gang.members.map((val, j) => (
                          <span key={j}>
                            <Link to={`member/${val.id}`}>{val.name}</Link>
                            {j === gang.members.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {gang.events
                      ? gang.events.map((val, j) => (
                          <span key={j}>
                            <Link to={`event/${val.id}`}>{val.id}</Link>
                            {j === gang.events.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${gang.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${gang.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${gang.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="frnzApp.gang.home.notFound">No Gangs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Gang;
