import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MemberStatus from './member-status';
import MemberStatusDetail from './member-status-detail';
import MemberStatusUpdate from './member-status-update';
import MemberStatusDeleteDialog from './member-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MemberStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MemberStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MemberStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={MemberStatus} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MemberStatusDeleteDialog} />
  </>
);

export default Routes;
