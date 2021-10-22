import { PlaceType } from 'app/shared/model/enumerations/place-type.model';

export interface IPlace {
  id?: string;
  name?: string;
  address?: string | null;
  type?: PlaceType | null;
  phone?: string | null;
  privatePlace?: boolean | null;
}

export const defaultValue: Readonly<IPlace> = {
  privatePlace: false,
};
