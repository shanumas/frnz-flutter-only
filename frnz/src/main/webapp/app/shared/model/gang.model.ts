import { IUser } from 'app/shared/model/user.model';
import { IMember } from 'app/shared/model/member.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IGang {
  id?: string;
  name?: string;
  handle?: string;
  htmlContent?: string | null;
  description?: string | null;
  announcement?: string | null;
  logo?: string | null;
  users?: IUser[] | null;
  members?: IMember[] | null;
  events?: IEvent[] | null;
}

export const defaultValue: Readonly<IGang> = {};
