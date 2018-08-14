import { RouteReuseStrategy, DetachedRouteHandle, ActivatedRouteSnapshot } from '@angular/router';

/*
 * Component reuse strategy based on
 * https://medium.com/@gerasimov.pk/how-to-reuse-rendered-component-in-angular-2-3-with-routereusestrategy-64628e1ca3eb
 */

export class CustomReuseStrategy implements RouteReuseStrategy {

  private handlers: {[key: string]: DetachedRouteHandle} = {};

  constructor() {
  }

  shouldDetach(route: ActivatedRouteSnapshot): boolean {
    return true;
  }

  store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle): void {
    this.handlers[route.url.join('/') || route.parent.url.join('/')] = handle;
  }

  shouldAttach(route: ActivatedRouteSnapshot): boolean {
    return !!this.handlers[route.url.join('/')];
  }

  retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle {
    return this.handlers[route.url.join('/') || route.parent.url.join('/')];
  }

  shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
    return future.routeConfig === curr.routeConfig;
  }

}
