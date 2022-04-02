import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import productionNetworkElement from './productionNetworkElement';
@Injectable()
export class HomeService {

  constructor(private http:HttpClient) { }
  public generateNetwork(productionNetwork : string):Observable<string>{
    return this.http.post("http://localhost:8080/generateNetwork", productionNetwork,{responseType:"text"})
  }

  public play():Observable<string>{
    return this.http.get("http://localhost:8080/play", {responseType:"text"})
  }

  public polling() :Observable<Object[]> {
    return this.http.get<Object[]>("http://localhost:8080/polling");

  }

  public stop() :Observable<string>{
    return this.http.get("http://localhost:8080/stop", {responseType:"text"});
  }
  public replay() :Observable<Object[][]> {
    return this.http.get<Object[][]>("http://localhost:8080/replay");

  }

}
