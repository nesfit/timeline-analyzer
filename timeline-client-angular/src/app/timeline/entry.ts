import { Timeline } from './timeline';

export class Entry {
  uri: string;
  sourceId: string;
  sourceTimeline: Timeline;
  timestamp: Date;
}
