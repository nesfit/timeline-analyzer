import { TAObject } from './taobject';

export class WebResource extends TAObject {
    title: string;
    url: string;

    objType(): string {
        return 'web';
    }
}
