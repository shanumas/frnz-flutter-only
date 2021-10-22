import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Gang from './gang';
import Place from './place';
import Event from './event';
import Moderator from './moderator';
import Member from './member';
import Participant from './participant';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}gang`} component={Gang} />
      <ErrorBoundaryRoute path={`${match.url}place`} component={Place} />
      <ErrorBoundaryRoute path={`${match.url}event`} component={Event} />
      <ErrorBoundaryRoute path={`${match.url}moderator`} component={Moderator} />
      <ErrorBoundaryRoute path={`${match.url}member`} component={Member} />
      <ErrorBoundaryRoute path={`${match.url}participant`} component={Participant} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
