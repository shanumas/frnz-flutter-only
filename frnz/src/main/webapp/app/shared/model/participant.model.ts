import { IMember } from 'app/shared/model/member.model';

export interface IParticipant {
  id?: string;
  sure?: boolean | null;
  host?: boolean | null;
  booker?: boolean | null;
  waiting?: boolean | null;
  share?: number | null;
  member?: IMember | null;
}

export const defaultValue: Readonly<IParticipant> = {
  sure: false,
  host: false,
  booker: false,
  waiting: false,
};
