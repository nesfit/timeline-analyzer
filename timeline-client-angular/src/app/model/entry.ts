import { TAObject } from './taobject';

export class Entry extends TAObject {
  sourceId: string;

  objType(): string {
      return 'entry';
  }
}
