import { TAObject } from './taobject';

export class LocalFile extends TAObject {
    fileName: string;
    path: string;

    objType(): string {
        return 'file';
    }
}
