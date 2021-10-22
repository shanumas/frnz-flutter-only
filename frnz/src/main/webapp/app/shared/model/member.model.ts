import { IGang } from 'app/shared/model/gang.model';

export interface IMember {
  id?: string;
  name?: string;
  email?: string | null;
  phone?: string | null;
  guest?: boolean | null;
  gang?: IGang | null;
}

export const defaultValue: Readonly<IMember> = {
  guest: false,
};
