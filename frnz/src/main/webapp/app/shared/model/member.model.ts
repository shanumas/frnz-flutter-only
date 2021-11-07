import { IEvent } from 'app/shared/model/event.model';
import { IGang } from 'app/shared/model/gang.model';

export interface IMember {
  id?: string;
  name?: string;
  email?: string | null;
  phone?: string | null;
  guest?: boolean | null;
  events?: IEvent[] | null;
  gangs?: IGang[] | null;
}

export const defaultValue: Readonly<IMember> = {
  guest: false,
};
