import { IMember } from 'app/shared/model/member.model';

export interface IMemberStatus {
  id?: number;
  sure?: boolean | null;
  booker?: boolean | null;
  waiting?: boolean | null;
  share?: number | null;
  member?: IMember | null;
}

export const defaultValue: Readonly<IMemberStatus> = {
  sure: false,
  booker: false,
  waiting: false,
};
