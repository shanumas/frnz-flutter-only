import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import gang from 'app/entities/gang/gang.reducer';
// prettier-ignore
import place from 'app/entities/place/place.reducer';
// prettier-ignore
import event from 'app/entities/event/event.reducer';
// prettier-ignore
import moderator from 'app/entities/moderator/moderator.reducer';
// prettier-ignore
import member from 'app/entities/member/member.reducer';
// prettier-ignore
import participant from 'app/entities/participant/participant.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  gang,
  place,
  event,
  moderator,
  member,
  participant,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
