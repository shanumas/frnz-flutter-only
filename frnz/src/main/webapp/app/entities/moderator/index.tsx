import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Moderator from './moderator';
import ModeratorDetail from './moderator-detail';
import ModeratorUpdate from './moderator-update';
import ModeratorDeleteDialog from './moderator-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ModeratorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ModeratorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ModeratorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Moderator} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ModeratorDeleteDialog} />
  </>
);

export default Routes;
