import { IUser } from 'app/shared/model/user.model';

export interface IGang {
  id?: string;
  name?: string;
  handle?: string;
  htmlContent?: string | null;
  description?: string | null;
  announcement?: string | null;
  logo?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IGang> = {};
