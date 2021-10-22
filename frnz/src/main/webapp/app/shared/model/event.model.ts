import dayjs from 'dayjs';
import { IPlace } from 'app/shared/model/place.model';
import { IParticipant } from 'app/shared/model/participant.model';
import { IGang } from 'app/shared/model/gang.model';
import { EventType } from 'app/shared/model/enumerations/event-type.model';

export interface IEvent {
  id?: string;
  type?: EventType | null;
  date?: string | null;
  name?: string | null;
  startTime?: string | null;
  endTime?: string | null;
  nonmembers?: string | null;
  confirmed?: boolean | null;
  cancelled?: boolean | null;
  recurring?: boolean | null;
  minimum?: number | null;
  maximum?: number | null;
  bookLimit?: number | null;
  cost?: number | null;
  share?: number | null;
  place?: IPlace | null;
  participant?: IParticipant | null;
  gang?: IGang | null;
}

export const defaultValue: Readonly<IEvent> = {
  confirmed: false,
  cancelled: false,
  recurring: false,
};
