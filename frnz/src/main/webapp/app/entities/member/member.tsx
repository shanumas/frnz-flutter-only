import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './member.reducer';
import { IMember } from 'app/shared/model/member.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Member = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const memberList = useAppSelector(state => state.member.entities);
  const loading = useAppSelector(state => state.member.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="member-heading" data-cy="MemberHeading">
        <Translate contentKey="frnzApp.member.home.title">Members</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="frnzApp.member.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="frnzApp.member.home.createLabel">Create new Member</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {memberList && memberList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="frnzApp.member.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.member.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.member.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.member.phone">Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.member.guest">Guest</Translate>
                </th>
                <th>
                  <Translate contentKey="frnzApp.member.gang">Gang</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {memberList.map((member, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${member.id}`} color="link" size="sm">
                      {member.id}
                    </Button>
                  </td>
                  <td>{member.name}</td>
                  <td>{member.email}</td>
                  <td>{member.phone}</td>
                  <td>{member.guest ? 'true' : 'false'}</td>
                  <td>{member.gang ? <Link to={`gang/${member.gang.id}`}>{member.gang.name}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${member.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${member.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${member.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="frnzApp.member.home.notFound">No Members found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Member;
