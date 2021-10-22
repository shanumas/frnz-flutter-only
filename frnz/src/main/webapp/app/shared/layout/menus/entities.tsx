import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/gang">
      <Translate contentKey="global.menu.entities.gang" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/place">
      <Translate contentKey="global.menu.entities.place" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/event">
      <Translate contentKey="global.menu.entities.event" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/moderator">
      <Translate contentKey="global.menu.entities.moderator" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/member">
      <Translate contentKey="global.menu.entities.member" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/participant">
      <Translate contentKey="global.menu.entities.participant" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
