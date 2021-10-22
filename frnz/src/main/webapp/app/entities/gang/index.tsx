import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Gang from './gang';
import GangDetail from './gang-detail';
import GangUpdate from './gang-update';
import GangDeleteDialog from './gang-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GangUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GangUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GangDetail} />
      <ErrorBoundaryRoute path={match.url} component={Gang} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GangDeleteDialog} />
  </>
);

export default Routes;
