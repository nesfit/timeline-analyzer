import { Timeline } from './timeline';

export class Event {
  uri: string;
  sourceTimeline: Timeline;
  timestamp: Date;
  label: string;
}
