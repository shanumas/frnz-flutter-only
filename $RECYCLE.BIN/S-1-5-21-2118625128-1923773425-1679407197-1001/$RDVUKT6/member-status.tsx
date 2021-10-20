import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './member-status.reducer';
import { IMemberStatus } from 'app/shared/model/member-status.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MemberStatus = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const memberStatusList = useAppSelector(state => state.memberStatus.entities);
  const loading = useAppSelector(state => state.memberStatus.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="member-status-heading" data-cy="MemberStatusHeading">
        <Translate contentKey="kompiApp.memberStatus.home.title">Member Statuses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="kompiApp.memberStatus.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kompiApp.memberStatus.home.createLabel">Create new Member Status</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {memberStatusList && memberStatusList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="kompiApp.memberStatus.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="kompiApp.memberStatus.sure">Sure</Translate>
                </th>
                <th>
                  <Translate contentKey="kompiApp.memberStatus.booker">Booker</Translate>
                </th>
                <th>
                  <Translate contentKey="kompiApp.memberStatus.waiting">Waiting</Translate>
                </th>
                <th>
                  <Translate contentKey="kompiApp.memberStatus.share">Share</Translate>
                </th>
                <th>
                  <Translate contentKey="kompiApp.memberStatus.member">Member</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {memberStatusList.map((memberStatus, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${memberStatus.id}`} color="link" size="sm">
                      {memberStatus.id}
                    </Button>
                  </td>
                  <td>{memberStatus.sure ? 'true' : 'false'}</td>
                  <td>{memberStatus.booker ? 'true' : 'false'}</td>
                  <td>{memberStatus.waiting ? 'true' : 'false'}</td>
                  <td>{memberStatus.share}</td>
                  <td>{memberStatus.member ? <Link to={`member/${memberStatus.member.id}`}>{memberStatus.member.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${memberStatus.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${memberStatus.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${memberStatus.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="kompiApp.memberStatus.home.notFound">No Member Statuses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MemberStatus;
