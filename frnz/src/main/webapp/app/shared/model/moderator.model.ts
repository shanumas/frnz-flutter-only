import { IGang } from 'app/shared/model/gang.model';
import { IMember } from 'app/shared/model/member.model';

export interface IModerator {
  id?: string;
  gang?: IGang | null;
  member?: IMember | null;
}

export const defaultValue: Readonly<IModerator> = {};
